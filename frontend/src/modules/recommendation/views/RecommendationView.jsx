import { useState } from 'react'
import {
  getAthlete,
  getAthleteSessions,
  getSessionExercises,

  getNextRecommendation,
  getCustomRecommendation,
} from '../api/recommendationApi.js'

export default function RecommendationView() {
  const [athleteIdInput, setAthleteIdInput] = useState('')


  const [athleteId, setAthleteId] = useState(null)


  const [athlete, setAthlete] = useState(null)


  const [sessions, setSessions] = useState([])
  const [selectedSessionId, setSelectedSessionId] = useState(null)
  const [sessionExercises, setSessionExercises] = useState([])
  const [rec, setRec] = useState(null)
  const [showCustomForm, setShowCustomForm] = useState(false)
  const [customGoal, setCustomGoal] = useState('')
  const [customLevel, setCustomLevel] = useState('')
  
  const [customEquipment, setCustomEquipment] = useState('')
  
  const [customAvailableMinutes, setCustomAvailableMinutes] = useState('')

  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  function athleteName(a) {
    if (!a) return ''

    if (a.fullName) return a.fullName

    if (a.name) return a.name

    return (a.firstName || '') + ' ' + (a.lastName || '')
  }

  function onChangeAthleteIdInput(e) {    // input
    setAthleteIdInput(e.target.value)
  }

  async function loadAthlete() {     //bouton charger
 
    var id = Number(athleteIdInput)

    if (!athleteIdInput || Number.isNaN(id) || id <= 0) {
      setError('Id invalide')
      return
    }

    setLoading(true)

    setError('')
    setRec(null)

    setSelectedSessionId(null)
    setSessionExercises([])

    try {
      var a = await getAthlete(id)

      var s = await getAthleteSessions(id)

      setAthleteId(id)

      setAthlete(a)
      setSessions(s)
    } catch (e) {

      setAthleteId(null)
      setAthlete(null)

      setSessions([])

      setError('Athlete introuvabl')
    }

    setLoading(false)
  }

  async function openSession(sessionId) {  // clik sur une séance


    setSelectedSessionId(sessionId)
    setSessionExercises([])

    setError('')

    try {

      var ex =await getSessionExercises(sessionId)

      setSessionExercises(ex)
    } catch (e) {
      setError(' impossible de charger les exercices')
    }
  }

  async function onRecommend() {   // pour btn recommender
    if (athleteId == null) return

    setLoading(true)

    setError('')

    try {
      var r = await getNextRecommendation(athleteId)
      setRec(r)

    } catch (e) {
      setError('impossible de recommander')
    }

    setLoading(false)
  }
  async function onCustomRecommend() {
  if (athleteId == null) return

  setLoading(true)
  setError('')

  try {
    var r = await getCustomRecommendation({
      athleteId: athleteId,
      goal: customGoal,
      level: customLevel,
      equipment: customEquipment,
      availableMinutes: customAvailableMinutes
        ? Number(customAvailableMinutes)
        : null,
    })

    setRec(r)
    setShowCustomForm(false)
  } catch (e) {
    setError('impossible de recommander')
  }

  setLoading(false)
}

  var athleteCard = null
  if (athlete) {
    athleteCard = (
      <div className="bg-white p-4 rounded shadow space-y-1">

        <div className="font-semibold">Athlete</div>
        <div>Id: {athlete.id}</div>

        <div>Nom: {athleteName(athlete)}</div>

        <div>Poids: {athlete.weightKg} kg</div>
        
        <div>objectif: {athlete.goal}</div>
        <div>niveau: {athlete.level}</div>
        
      </div>
    )
  }

  var recCard = null
  if (rec) {
    var exs = rec.exercises || []

    var reasons = rec.reasons || []

    recCard = (
      <div className="bg-white p-4 rounded shadow space-y-3">

        <div className="font-semibold">Recommandation</div>
        <div>Focus: {rec.focusMuscleGroup}</div>

        <div>Intensite: {rec.intensity}</div>
        <div>Duree: {rec.availableMinutes} min</div>

        <div>
          <div className="font-semibold mb-2">Exercices</div>
          <ul className="list-disc ml-5">

            {exs.map(function (e) {
              return <li key={e.id}>{e.name}</li>
            })}
          </ul>

        </div>

        <div>
          <div className="font-semibold mb-2">Raison</div>

          <ul className="list-disc ml-5">
            {reasons.map(function (x, idx) {
              return <li key={idx}>{x}</li>
            })}
          </ul>
        </div>
      </div>
    )
  }

  return (
    <div className="space-y-4">

      <h1 className="text-2xl font-semibold">  Recommendation</h1>

      <div className="bg-white p-4 rounded shadow space-y-3">
        <div className="flex gap-3 items-center flex-wrap">
          <input
              className="border rounded p-2 w-48"

            placeholder="Athlete id"
            value={athleteIdInput}

            onChange={onChangeAthleteIdInput}
          />

          <button

            className="bg-blue-600 text-white px-4 py-2 rounded disabled:opacity-50"
            onClick={loadAthlete}

            disabled={loading}
          >
            {loading ? 'Loading...' : 'Charger'}
          </button>

          <button
            className="bg-green-600 text-white px-4 py-2 rounded disabled:opacity-50"
            onClick={onRecommend}
            disabled={loading || athleteId == null}
          >
            {loading ? 'Loading...' : 'Recommander la prochaine séance'}
          </button>
        </div>

        {error ? <div className="text-red-600">{error}</div> : null}
      </div>

      {athleteCard}

      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div className="bg-white p-4 rounded shadow">

          <div className="font-semibold mb-2">historique des seances</div>

          {sessions.length === 0 ? <div>Aucune seance</div> : null}

          <div className="flex flex-col gap-2">


            {sessions.map(function (s) {

                var cls = 'text-left border rounded p-3'
              if (selectedSessionId === s.id) {
                  cls =cls + ' bg-gray-100'
              }

              return (
                <button
                  key={s.id}
                  className={cls}

                  onClick={function () {

                    openSession(s.id)
                  }}
                >
                  <div>Date: {s.sessionDate}</div>
                  <div>Calories: {s.totalCalories}</div>
                  <div>Duree: {s.durationMinutes} min</div>

                  <div>Intensite: {s.sessionIntensity}</div>

                </button>

              )

            })}

          </div>
        </div>

        <div className="bg-white p-4 rounded shadow">
          <div className="font-semibold mb-2">Exercices de la seance</div>

          {selectedSessionId == null ? <div>Cliquer sur une seance</div> : null}

          {selectedSessionId != null ? (
            <div className="flex flex-col gap-2">

              {sessionExercises.map(function (  se) {
                var exName = ''

                var muscle = ''

                if (se && se.exercise) {
                  exName = se.exercise.name || ''

                  if (se.exercise.muscleGroup) {
                    muscle = se.exercise.muscleGroup.name || ''
                  }
                }

                return (
                  <div key={se.id} className="border rounded p-3">
                    <div className="font-medium">{exName}</div>
                    <div>Muscle: {muscle}</div>
                    <div>Duree: {se.durationMinutes} min</div>
                    <div>Intensite: {se.intensity}</div>
                    <div>calories: {se.caloriesBurned}</div>
                  </div>
                )

              })}

              {sessionExercises.length === 0 ? <div>Aucun exercice</div> : null}
            </div>
            
          ) : null}
        </div>
      </div>

      {recCard}
    </div>
  )
}
import { useEffect, useMemo, useState } from "react";
import "./App.css";

const API_BASE = "http://localhost:8080";

function formatTodayYYYYMMDD() {
  const d = new Date();
  const yyyy = d.getFullYear();
  const mm = String(d.getMonth() + 1).padStart(2, "0");
  const dd = String(d.getDate()).padStart(2, "0");
  return `${yyyy}-${mm}-${dd}`;
}

export default function App() {
  
  const [athleteIdInput, setAthleteIdInput] = useState("");
  const [athlete, setAthlete] = useState(null);
  const athleteId = athlete?.id ?? null;

  
  const [muscles, setMuscles] = useState([]);
  const [selectedMuscleId, setSelectedMuscleId] = useState("");
  const [exercises, setExercises] = useState([]);
  const [selectedExerciseId, setSelectedExerciseId] = useState("");

  
  const [sessionDate, setSessionDate] = useState(formatTodayYYYYMMDD);
  const [durationMinutes, setDurationMinutes] = useState(20);
  const [intensity, setIntensity] = useState("MEDIUM");
  const [items, setItems] = useState([]);

  
  const [result, setResult] = useState(null);
  const [sessions, setSessions] = useState([]); 
  const [expanded, setExpanded] = useState({}); 

  const [message, setMessage] = useState("");

  
  useEffect(() => {
    fetch(API_BASE + "/api/muscles")
      .then((res) => res.json())
      .then((data) => setMuscles(data))
      .catch(() => setMessage("Erreur: impossible de charger les muscles. Vérifie le backend."));
  }, []);

  useEffect(() => {
    if (!selectedMuscleId) {
      setExercises([]);
      setSelectedExerciseId("");
      return;
    }

    fetch(API_BASE + "/api/muscles/" + selectedMuscleId + "/exercises")
      .then((res) => res.json())
      .then((data) => {
        setExercises(data);
        setSelectedExerciseId("");
      })
      .catch(() => setMessage("Erreur: impossible de charger les exercices."));
  }, [selectedMuscleId]);

  function resetSessionBuilder() {
    setSelectedMuscleId("");
    setExercises([]);
    setSelectedExerciseId("");
    setIntensity("MEDIUM");
    setDurationMinutes(20);
    setItems([]);
    setResult(null);
    setExpanded({});
  }

  function loadSessionsHistory(id) {
    return fetch(API_BASE + "/api/athletes/" + id + "/sessions")
      .then((res) => res.json())
      .then((data) => {
        setSessions(Array.isArray(data) ? data : []);
      })
      .catch(() => setMessage("Erreur: impossible de charger l'historique des séances."));
  }

  function loadAthleteById() {
    setMessage("");
    setResult(null);

    const id = String(athleteIdInput || "").trim();
    if (!id) {
      setMessage("Entre un Athlete ID.");
      return;
    }
    if (!/^\d+$/.test(id)) {
      setMessage("Athlete ID doit être un nombre.");
      return;
    }

    fetch(API_BASE + "/api/athletes/" + id)
      .then((res) => {
        if (!res.ok) throw new Error("NOT_OK");
        return res.json();
      })
      .then((data) => {
        setAthlete(data);
        setMessage(
          `Athlète chargé: #${data.id} — ${data.firstName ?? ""} ${data.lastName ?? ""} — Poids: ${data.weightKg} kg`
        );
        resetSessionBuilder();
        return loadSessionsHistory(data.id);
      })
      .catch(() => {
        setAthlete(null);
        setSessions([]);
        setMessage("Athlete introuvable (vérifie l'ID).");
      });
  }

  function addItem() {
    setMessage("");
    setResult(null);

    if (!athleteId) {
      setMessage("Charge d'abord un athlete (Athlete ID).");
      return;
    }
    if (!selectedExerciseId) {
      setMessage("Choisis un exercice.");
      return;
    }

    const ex = exercises.find((e) => String(e.id) === String(selectedExerciseId));
    if (!ex) {
      setMessage("Exercice introuvable.");
      return;
    }

    const newItem = {
      exerciseId: ex.id,
      exerciseName: ex.name,
      durationMinutes: Number(durationMinutes),
      intensity: intensity,
    };

    setItems((prev) => [...prev, newItem]);
  }

  function removeItem(index) {
    setItems((prev) => prev.filter((_, i) => i !== index));
  }

  function submitSession() {
    setMessage("");
    setResult(null);

    if (!athleteId) {
      setMessage("Charge d'abord un athlete (Athlete ID).");
      return;
    }
    if (!items.length) {
      setMessage("Ajoute au moins un exercice à la séance.");
      return;
    }

    const body = {
      sessionDate: sessionDate,
      items: items.map((it) => ({
        exerciseId: it.exerciseId,
        durationMinutes: it.durationMinutes,
        intensity: it.intensity,
      })),
    };

    fetch(API_BASE + "/api/athletes/" + athleteId + "/sessions", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(body),
    })
      .then((res) => {
        if (!res.ok) throw new Error("NOT_OK");
        return res.json();
      })
      .then((data) => {
        setResult(data);
        setMessage("Séance enregistrée !");
        setItems([]);
        return loadSessionsHistory(athleteId);
      })
      .catch(() => setMessage("Erreur: enregistrement séance échoué."));
  }

  function toggleSession(sessionId) {
    setExpanded((prev) => ({ ...prev, [sessionId]: !prev[sessionId] }));
  }

  const athleteLabel = useMemo(() => {
    if (!athlete) return "";
    const full = `${athlete.firstName ?? ""} ${athlete.lastName ?? ""}`.trim();
    return full ? `${full} (Poids: ${athlete.weightKg} kg)` : `Poids: ${athlete.weightKg} kg`;
  }, [athlete]);

  return (
    <div className="container">
      <h1>Fitness Goal Road </h1>

      {message ? <div className="message">{message}</div> : null}

      {/* Athlete */}
      <div className="card">
        <h2>Athlète</h2>

        <div className="row">
          <div className="field">
            <label>Athlete ID</label>
            <input
              value={athleteIdInput}
              onChange={(e) => setAthleteIdInput(e.target.value)}
              onKeyDown={(e) => {
                if (e.key === "Enter") loadAthleteById();
              }}
              placeholder="ex: 1"
            />
            <div className="muted small">Appuie sur Enter pour charger.</div>
          </div>
        </div>

        <button className="primary" onClick={loadAthleteById}>
          Charger
        </button>

        {athleteId ? (
          <div className="info">
            Athlete: <b>#{athleteId}</b> — {athleteLabel}
          </div>
        ) : (
          <div className="muted">Aucun athlète chargé.</div>
        )}
      </div>

      {/* session */}
      <div className="card">
        <h2>Séance</h2>

        <div className="row">
          <div className="field">
            <label>Date de séance</label>
            <input
              type="date"
              value={sessionDate}
              onChange={(e) => setSessionDate(e.target.value)}
              disabled={!athleteId}
            />
          </div>
        </div>

        <h3>Choisir Muscle → Exercice → Intensité → Durée</h3>

        <div className="row">
          <div className="field">
            <label>Muscle</label>
            <select
              value={selectedMuscleId}
              onChange={(e) => setSelectedMuscleId(e.target.value)}
              disabled={!athleteId}
            >
              <option value="">-- choisir muscle --</option>
              {muscles.map((m) => (
                <option key={m.id} value={m.id}>
                  {m.name}
                </option>
              ))}
            </select>
          </div>

          <div className="field">
            <label>Exercice</label>
            <select
              value={selectedExerciseId}
              onChange={(e) => setSelectedExerciseId(e.target.value)}
              disabled={!athleteId || !exercises.length}
            >
              <option value="">-- choisir exercice --</option>
              {exercises.map((ex) => (
                <option key={ex.id} value={ex.id}>
                  {ex.name}
                </option>
              ))}
            </select>
          </div>

          <div className="field">
            <label>Intensité</label>
            <select value={intensity} onChange={(e) => setIntensity(e.target.value)} disabled={!athleteId}>
              <option value="LOW">LOW</option>
              <option value="MEDIUM">MEDIUM</option>
              <option value="HIGH">HIGH</option>
            </select>
          </div>

          <div className="field">
            <label>Durée (minutes)</label>
            <input
              type="number"
              value={durationMinutes}
              onChange={(e) => setDurationMinutes(e.target.value)}
              min="1"
              disabled={!athleteId}
            />
          </div>
        </div>

        <button onClick={addItem} disabled={!athleteId || !selectedExerciseId}>
          Ajouter à la séance
        </button>

        <h3>Exercices ajoutés</h3>
        {items.length === 0 ? (
          <div className="muted">Aucun exercice ajouté.</div>
        ) : (
          <table>
            <thead>
              <tr>
                <th>Exercice</th>
                <th>Durée</th>
                <th>Intensité</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {items.map((it, idx) => (
                <tr key={idx}>
                  <td>{it.exerciseName}</td>
                  <td>{it.durationMinutes} min</td>
                  <td>{it.intensity}</td>
                  <td>
                    <button className="danger" onClick={() => removeItem(idx)}>
                      Supprimer
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}

        <button className="primary" onClick={submitSession} disabled={!athleteId}>
          Enregistrer la séance + Calculer calories brûlées
        </button>
      </div>

      {/* Result */}
      <div className="card">
        <h2>Résultat (calories brûlées par exercice + total)</h2>

        {!result ? (
          <div className="muted">Pas de résultat pour le moment.</div>
        ) : (
          <div>
            <div className="info">
              Séance #{result.id} — Date: {result.sessionDate}
            </div>

            <table>
              <thead>
                <tr>
                  <th>Exercice</th>
                  <th>Durée</th>
                  <th>Intensité</th>
                  <th>Calories</th>
                </tr>
              </thead>
              <tbody>
                {result.items.map((it, idx) => (
                  <tr key={idx}>
                    <td>{it.exerciseName}</td>
                    <td>{it.durationMinutes} min</td>
                    <td>{it.intensity}</td>
                    <td>{Number(it.caloriesBurned).toFixed(2)}</td>
                  </tr>
                ))}
              </tbody>
            </table>

            <div className="total">
              Total calories brûlées pendant cette séance: <b>{Number(result.totalCalories).toFixed(2)}</b>
            </div>
          </div>
        )}
      </div>

      
    </div>
  );
}

import React, { useState } from 'react'
import ResultCard from '../components/ResultCard'

export default function MacroPage() {
    const [formData, setFormData] = useState({
        birthYear: '',
        gender: '1',
        weight: '',
        height: '',
        activity: '1',
        goal: '1',
        gainType: '1'
    })

    const [results, setResults] = useState(null)
    const [loading, setLoading] = useState(false)

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value })
    }

    const handleSubmit = async (e) => {
        e.preventDefault()
        setLoading(true)

        // Simulate API call
        setTimeout(() => {
            setResults({
                calories: 3063,
                protein: 156.0,
                carbs: 418.3,
                fats: 85.1
            })
            setLoading(false)
        }, 1000)
    }

    return (
        <div className="max-w-2xl mx-auto p-6 bg-gray-50 min-h-screen">
            <h1 className="text-3xl font-bold text-gray-800 mb-6 text-center">Calculateur de Macros</h1>

            <form onSubmit={handleSubmit} className="bg-white p-6 rounded-lg shadow-sm space-y-5">

                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div>
                        <label className="block text-sm font-medium mb-1">Année de naissance</label>
                        <input
                            type="number"
                            name="birthYear"
                            value={formData.birthYear}
                            onChange={handleChange}
                            placeholder="Ex: 2003"
                            className="w-full p-2 border rounded focus:outline-blue-500"
                            required
                        />
                    </div>

                    <div>
                        <label className="block text-sm font-medium mb-1">Sexe</label>
                        <select
                            name="gender"
                            value={formData.gender}
                            onChange={handleChange}
                            className="w-full p-2 border rounded focus:outline-blue-500"
                        >
                            <option value="1">Homme</option>
                            <option value="2">Femme</option>
                        </select>
                    </div>

                    <div>
                        <label className="block text-sm font-medium mb-1">Poids actuel (kg)</label>
                        <input
                            type="number"
                            name="weight"
                            step="0.1"
                            value={formData.weight}
                            onChange={handleChange}
                            placeholder="Ex: 78"
                            className="w-full p-2 border rounded focus:outline-blue-500"
                            required
                        />
                    </div>

                    <div>
                        <label className="block text-sm font-medium mb-1">Taille (m)</label>
                        <input
                            type="number"
                            name="height"
                            step="0.01"
                            value={formData.height}
                            onChange={handleChange}
                            placeholder="Ex: 1.78"
                            className="w-full p-2 border rounded focus:outline-blue-500"
                            required
                        />
                    </div>
                </div>

                <div>
                    <label className="block text-sm font-medium mb-1">Niveau d'Activité</label>
                    <select
                        name="activity"
                        value={formData.activity}
                        onChange={handleChange}
                        className="w-full p-2 border rounded focus:outline-blue-500"
                    >
                        <option value="1">Sédentaire (Peu ou pas d'exercice)</option>
                        <option value="2">Modéré (Exercice 1-3 fois/semaine)</option>
                        <option value="3">Actif (Exercice 3-5 fois/semaine)</option>
                        <option value="4">Très Actif (Exercice 6-7 fois/semaine)</option>
                    </select>
                </div>

                <div>
                    <label className="block text-sm font-medium mb-1">Objectif Principal</label>
                    <select
                        name="goal"
                        value={formData.goal}
                        onChange={handleChange}
                        className="w-full p-2 border rounded focus:outline-blue-500"
                    >
                        <option value="1">Gagner du poids</option>
                        <option value="2">Perdre du poids</option>
                    </select>
                </div>

                {formData.goal === '1' && (
                    <div className="animate-fade-in">
                        <label className="block text-sm font-medium mb-1">Précisez votre objectif de gain</label>
                        <select
                            name="gainType"
                            value={formData.gainType}
                            onChange={handleChange}
                            className="w-full p-2 border rounded focus:outline-blue-500 bg-blue-50"
                        >
                            <option value="1">Prise de poids générale</option>
                            <option value="2">Prise de masse musculaire</option>
                        </select>
                    </div>
                )}

                <button
                    type="submit"
                    disabled={loading}
                    className="w-full bg-blue-600 text-white py-3 rounded font-semibold hover:bg-blue-700 transition"
                >
                    {loading ? 'Calcul en cours...' : 'Calculer mes besoins'}
                </button>
            </form>

            {results && (
                <div className="mt-8">
                    <h2 className="text-xl font-bold mb-4 text-center">Résultat Nutritionnel Initial</h2>

                    <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-8">
                        <ResultCard label="Calories" value={results.calories} unit="kcal" />
                        <ResultCard label="Protéines" value={results.protein} unit="g" />
                        <ResultCard label="Glucides" value={results.carbs} unit="g" />
                        <ResultCard label="Lipides" value={results.fats} unit="g" />
                    </div>

                    <div className="text-center">
                        <button className="bg-green-600 text-white px-8 py-3 rounded-full font-bold shadow-lg hover:bg-green-700 hover:scale-105 transition-transform">
                            Suivre mon avancement
                        </button>
                    </div>
                </div>
            )}
        </div>
    )
}
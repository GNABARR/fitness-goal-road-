import React from 'react'

export default function ResultCard({ label, value, unit }) {
    return (
        <div className="bg-white p-4 rounded-lg shadow border text-center">
            <p className="text-gray-500 text-sm uppercase font-bold tracking-wide">{label}</p>
            <p className="text-2xl font-bold text-blue-600">
                {value} <span className="text-sm text-gray-400 font-normal">{unit}</span>
            </p>
        </div>
    )
}
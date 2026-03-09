const BASE = `${import.meta.env.VITE_API_URL || ''}/api/fitness`;


export const calculateNutrition = async (data) => {
    const res = await fetch(`${BASE}/calculer`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
    });
    if (!res.ok) throw new Error('Failed to calculate nutrition');
    return res.json();
};

export const adjustNutrition = async (data) => {
    const res = await fetch(`${BASE}/ajuster`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
    });
    if (!res.ok) throw new Error('Failed to adjust nutrition');
    return res.json();
};

export const calculateMicronutrition = async (data) => {
    const res = await fetch(`${BASE}/micro`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
    });
    if (!res.ok) throw new Error('Failed to calculate micronutrition');
    return res.json();
};
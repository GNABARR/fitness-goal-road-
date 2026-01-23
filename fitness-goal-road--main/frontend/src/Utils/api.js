const API_BASE_URL = 'http://localhost:8080/api/fitness';

export const calculateNutrition = async (data) => {
    const response = await fetch(`${API_BASE_URL}/calculer`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    });

    if (!response.ok) {
        throw new Error('Failed to calculate nutrition');
    }

    return response.json();
};

export const adjustNutrition = async (data) => {
    const response = await fetch(`${API_BASE_URL}/ajuster`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    });

    if (!response.ok) {
        throw new Error('Failed to adjust nutrition');
    }

    return response.json();
};
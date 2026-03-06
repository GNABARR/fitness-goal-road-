export type BmiRequest = {
  poidsKg: number;
  tailleCm: number;
};

export type BmiResponse = {
  id: number;
  poidsKg: number;
  tailleCm: number;
  bmi: number;
  categorie: string;
};

const API_BASE_URL = "/api";

export async function calculateBmi(data: BmiRequest): Promise<BmiResponse> {
  const response = await fetch(`${API_BASE_URL}/bmi`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    throw new Error("Failed to calculate BMI");
  }

  return response.json();
}
export type AuthResponse = {
  id: number;
  nom: string;
  prenom: string;
  email: string;
  message: string;
};

export type RegisterRequest = {
  nom: string;
  prenom: string;
  email: string;
  password: string;
};

export type LoginRequest = {
  email: string;
  password: string;
};

const API_BASE_URL = "/api";
const STORAGE_KEY = "currentUser";

export async function register(data: RegisterRequest): Promise<AuthResponse> {
  const response = await fetch(`${API_BASE_URL}/auth/register`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  });

  const body = await response.json();

  if (!response.ok) {
    throw new Error(body.message || body.error || "Registration erreur");
  }

  saveCurrentUser(body);
  return body;
}

export async function login(data: LoginRequest): Promise<AuthResponse> {
  const response = await fetch(`${API_BASE_URL}/auth/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  });

  const body = await response.json();

  if (!response.ok) {
    throw new Error(body.message || body.error || "Login erreur");
  }

  saveCurrentUser(body);
  return body;
}

export function saveCurrentUser(user: AuthResponse) {
  localStorage.setItem(STORAGE_KEY, JSON.stringify(user));
}

export function getCurrentUser(): AuthResponse | null {
  const raw = localStorage.getItem(STORAGE_KEY);

  if (!raw) {
    return null;
  }

  try {
    return JSON.parse(raw) as AuthResponse;
  } catch {
    return null;
  }
}

export function getCurrentUserId(): number | null {
  const user = getCurrentUser();
  return user ? user.id : null;
}

export function logout() {
  localStorage.removeItem(STORAGE_KEY);
}
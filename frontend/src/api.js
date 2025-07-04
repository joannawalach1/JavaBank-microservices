const API_BASE_URL = 'http://localhost:8080';

export async function getUser(userId) {
  const response = await fetch(`${API_BASE_URL}/users/${userId}`);
  if (!response.ok) throw new Error('Network error');
  return await response.json();
}
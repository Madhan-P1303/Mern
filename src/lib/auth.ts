import { authAPI, User, LoginResponse } from './api-services';

// Authentication utilities that integrate with the backend API

export const getCurrentUser = (): User | null => {
  // Check localStorage for user info
  const userStr = localStorage.getItem('currentUser');
  if (userStr) {
    try {
      return JSON.parse(userStr);
    } catch (error) {
      console.error('Error parsing user data:', error);
      return null;
    }
  }
  return null;
};

export const getCurrentUserName = (): string | null => {
  const user = getCurrentUser();
  return user ? user.name : null;
};

export const logoutUser = (): void => {
  // Remove user and token from localStorage
  localStorage.removeItem('currentUser');
  localStorage.removeItem('jwt_token');
};

export const setCurrentUser = (user: User): void => {
  // Store user in localStorage
  localStorage.setItem('currentUser', JSON.stringify(user));
};

export const getToken = (): string | null => {
  return localStorage.getItem('jwt_token');
};

export const setToken = (token: string): void => {
  localStorage.setItem('jwt_token', token);
};

export const isAuthenticated = (): boolean => {
  const token = getToken();
  const user = getCurrentUser();
  return !!(token && user);
};

export const registerUser = async (name: string, email: string, password: string): Promise<User> => {
  try {
    const user = await authAPI.signup({ name, email, password });
    setCurrentUser(user);
    return user;
  } catch (error: any) {
    throw new Error(error.response?.data || 'Registration failed');
  }
};

export const loginUser = async (email: string, password: string): Promise<LoginResponse> => {
  try {
    const response = await authAPI.login({ email, password });
    
    // Store token and user info
    setToken(response.token);
    setCurrentUser({
      id: response.id,
      name: response.name,
      email: response.email,
      role: response.role as 'STUDENT' | 'INSTRUCTOR' | 'ADMIN',
      createdAt: new Date().toISOString(), // We don't get this from login response
    });
    
    return response;
  } catch (error: any) {
    throw new Error(error.response?.data || 'Login failed');
  }
};

export const getCurrentUserFromAPI = async (): Promise<User> => {
  try {
    const user = await authAPI.getCurrentUser();
    setCurrentUser(user);
    return user;
  } catch (error: any) {
    // If API call fails, clear local storage
    logoutUser();
    throw new Error(error.response?.data || 'Failed to get current user');
  }
};

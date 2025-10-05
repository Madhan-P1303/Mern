import api from './api';

// Types for API responses
export interface User {
  id: number;
  name: string;
  email: string;
  role: 'STUDENT' | 'INSTRUCTOR' | 'ADMIN';
  createdAt: string;
  currentStreak?: number;
  lastActivityDate?: string;
}

export interface Course {
  id: number;
  title: string;
  description: string;
  category: string;
  level: 'BEGINNER' | 'INTERMEDIATE' | 'ADVANCED';
  instructorId: number;
  instructorName: string;
  duration: number;
  lessons: number;
  studentsEnrolled: number;
  createdAt: string;
  updatedAt: string;
}

export interface Enrollment {
  id: number;
  userId: number;
  userName: string;
  courseId: number;
  courseTitle: string;
  progress: number;
  completionStatus: 'NOT_STARTED' | 'IN_PROGRESS' | 'COMPLETED';
  createdAt: string;
  updatedAt: string;
}

export interface Achievement {
  id: number;
  userId: number;
  userName: string;
  title: string;
  description: string;
  type: string;
  earnedDate: string;
}

export interface Dashboard {
  userId: number;
  userName: string;
  totalEnrolledCourses: number;
  completedCourses: number;
  inProgressCourses: number;
  totalHours: number;
  currentStreak: number;
  totalAchievements: number;
  recentEnrollments: Enrollment[];
  recentAchievements: Achievement[];
  lastActivityDate: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface SignupRequest {
  name: string;
  email: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  type: string;
  id: number;
  name: string;
  email: string;
  role: string;
}

export interface ProgressUpdateRequest {
  progress: number;
}

// Auth API functions
export const authAPI = {
  login: async (data: LoginRequest): Promise<LoginResponse> => {
    const response = await api.post('/auth/login', data);
    return response.data;
  },

  signup: async (data: SignupRequest): Promise<User> => {
    const response = await api.post('/auth/signup', data);
    return response.data;
  },

  getCurrentUser: async (): Promise<User> => {
    const response = await api.get('/auth/me');
    return response.data;
  },
};

// Courses API functions
export const coursesAPI = {
  getAll: async (): Promise<Course[]> => {
    const response = await api.get('/courses');
    return response.data;
  },

  getById: async (id: number): Promise<Course> => {
    const response = await api.get(`/courses/${id}`);
    return response.data;
  },

  create: async (data: Omit<Course, 'id' | 'instructorId' | 'instructorName' | 'studentsEnrolled' | 'createdAt' | 'updatedAt'>): Promise<Course> => {
    const response = await api.post('/courses', data);
    return response.data;
  },

  update: async (id: number, data: Partial<Course>): Promise<Course> => {
    const response = await api.put(`/courses/${id}`, data);
    return response.data;
  },

  delete: async (id: number): Promise<void> => {
    await api.delete(`/courses/${id}`);
  },

  getByCategory: async (category: string): Promise<Course[]> => {
    const response = await api.get(`/courses/category/${category}`);
    return response.data;
  },

  getByLevel: async (level: string): Promise<Course[]> => {
    const response = await api.get(`/courses/level/${level}`);
    return response.data;
  },

  search: async (query: string): Promise<Course[]> => {
    const response = await api.get(`/courses/search?q=${encodeURIComponent(query)}`);
    return response.data;
  },

  getPopular: async (): Promise<Course[]> => {
    const response = await api.get('/courses/popular');
    return response.data;
  },

  getRecent: async (): Promise<Course[]> => {
    const response = await api.get('/courses/recent');
    return response.data;
  },
};

// Enrollments API functions
export const enrollmentsAPI = {
  enroll: async (courseId: number): Promise<Enrollment> => {
    const response = await api.post(`/enroll/${courseId}`);
    return response.data;
  },

  updateProgress: async (courseId: number, data: ProgressUpdateRequest): Promise<Enrollment> => {
    const response = await api.put(`/enroll/${courseId}/progress`, data);
    return response.data;
  },

  getMyCourses: async (): Promise<Enrollment[]> => {
    const response = await api.get('/enroll/my-courses');
    return response.data;
  },

  getMyCourse: async (courseId: number): Promise<Enrollment> => {
    const response = await api.get(`/enroll/my-courses/${courseId}`);
    return response.data;
  },

  unenroll: async (courseId: number): Promise<void> => {
    await api.delete(`/enroll/${courseId}`);
  },

  getCourseEnrollments: async (courseId: number): Promise<Enrollment[]> => {
    const response = await api.get(`/enroll/course/${courseId}`);
    return response.data;
  },

  getCourseStats: async (courseId: number): Promise<{ enrollmentCount: number }> => {
    const response = await api.get(`/enroll/stats/${courseId}`);
    return response.data;
  },
};

// Achievements API functions
export const achievementsAPI = {
  getMyAchievements: async (): Promise<Achievement[]> => {
    const response = await api.get('/achievements');
    return response.data;
  },

  getUserAchievements: async (userId: number): Promise<Achievement[]> => {
    const response = await api.get(`/achievements/user/${userId}`);
    return response.data;
  },

  getMyAchievementCount: async (): Promise<{ achievementCount: number }> => {
    const response = await api.get('/achievements/count');
    return response.data;
  },

  getAchievementTypes: async (): Promise<string[]> => {
    const response = await api.get('/achievements/types');
    return response.data;
  },
};

// Dashboard API functions
export const dashboardAPI = {
  getDashboard: async (): Promise<Dashboard> => {
    const response = await api.get('/dashboard');
    return response.data;
  },

  getUserDashboard: async (userId: number): Promise<Dashboard> => {
    const response = await api.get(`/dashboard/user/${userId}`);
    return response.data;
  },
};









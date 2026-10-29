export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  password: string;
  fullName: string;
  email: string;
}

export interface LoginResponse {
  authenticated: boolean;
  userId?: number;
  username?: string;
  fullName?: string;
  role?: string;
  token?: string;
  message?: string;
}

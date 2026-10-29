import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { LoginRequest, LoginResponse, RegisterRequest } from '../models/admin-user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly apiUrl = typeof window !== 'undefined' && window.location.port === '4200'
    ? 'http://localhost:8080/api/auth'
    : '/api/auth';
  private readonly STORAGE_KEY = 'hw_user_session';

  private currentUserSubject = new BehaviorSubject<LoginResponse | null>(this.getStoredSession());
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {}

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, request).pipe(
      tap((response: LoginResponse) => {
        if (response.authenticated) {
          localStorage.setItem(this.STORAGE_KEY, JSON.stringify(response));
          this.currentUserSubject.next(response);
        }
      })
    );
  }

  register(request: RegisterRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/register`, request).pipe(
      tap((response: LoginResponse) => {
        if (response.authenticated) {
          localStorage.setItem(this.STORAGE_KEY, JSON.stringify(response));
          this.currentUserSubject.next(response);
        }
      })
    );
  }

  logout(): void {
    localStorage.removeItem(this.STORAGE_KEY);
    this.currentUserSubject.next(null);
  }

  isAuthenticated(): boolean {
    const session = this.currentUserSubject.value;
    return !!(session && session.authenticated);
  }

  isAdmin(): boolean {
    const session = this.currentUserSubject.value;
    return !!(session && session.authenticated && session.role === 'ROLE_ADMIN');
  }

  isVisitor(): boolean {
    const session = this.currentUserSubject.value;
    return !!(session && session.authenticated && session.role === 'ROLE_VISITOR');
  }

  getUserId(): number | undefined {
    return this.currentUserSubject.value?.userId;
  }

  getCurrentUser(): LoginResponse | null {
    return this.currentUserSubject.value;
  }

  private getStoredSession(): LoginResponse | null {
    try {
      const data = localStorage.getItem(this.STORAGE_KEY);
      return data ? JSON.parse(data) : null;
    } catch (e) {
      return null;
    }
  }
}

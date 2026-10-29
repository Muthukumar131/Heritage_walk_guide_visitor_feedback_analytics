import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { VisitorDashboardDTO } from '../models/visitor-dashboard.model';

@Injectable({
  providedIn: 'root'
})
export class VisitorService {
  private readonly apiUrl = typeof window !== 'undefined' && window.location.port === '4200'
    ? 'http://localhost:8080/api/visitor'
    : '/api/visitor';

  constructor(private http: HttpClient) {}

  getDashboardData(userId?: number, email?: string): Observable<VisitorDashboardDTO> {
    let params = new HttpParams();
    if (userId) params = params.set('userId', userId.toString());
    if (email) params = params.set('email', email);
    return this.http.get<VisitorDashboardDTO>(`${this.apiUrl}/dashboard`, { params });
  }
}

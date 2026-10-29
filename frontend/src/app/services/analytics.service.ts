import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AnalyticsOverview } from '../models/analytics.model';

@Injectable({
  providedIn: 'root'
})
export class AnalyticsService {
  private readonly apiUrl = typeof window !== 'undefined' && window.location.port === '4200'
    ? 'http://localhost:8080/api/analytics'
    : '/api/analytics';

  constructor(private http: HttpClient) {}

  getAnalyticsOverview(): Observable<AnalyticsOverview> {
    return this.http.get<AnalyticsOverview>(`${this.apiUrl}/overview`);
  }
}

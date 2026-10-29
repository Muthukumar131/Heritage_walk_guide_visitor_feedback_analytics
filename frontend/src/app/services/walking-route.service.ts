import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { WalkingRoute } from '../models/walking-route.model';
import { RouteStop, RouteStopDTO } from '../models/route-stop.model';

@Injectable({
  providedIn: 'root'
})
export class WalkingRouteService {
  private readonly apiUrl = typeof window !== 'undefined' && window.location.port === '4200'
    ? 'http://localhost:8080/api/routes'
    : '/api/routes';

  constructor(private http: HttpClient) {}

  getAllRoutes(): Observable<WalkingRoute[]> {
    return this.http.get<WalkingRoute[]>(this.apiUrl);
  }

  getRouteById(id: number): Observable<WalkingRoute> {
    return this.http.get<WalkingRoute>(`${this.apiUrl}/${id}`);
  }

  createRoute(route: WalkingRoute): Observable<WalkingRoute> {
    return this.http.post<WalkingRoute>(this.apiUrl, route);
  }

  updateRoute(id: number, route: WalkingRoute): Observable<WalkingRoute> {
    return this.http.put<WalkingRoute>(`${this.apiUrl}/${id}`, route);
  }

  deleteRoute(id: number): Observable<{ message: string }> {
    return this.http.delete<{ message: string }>(`${this.apiUrl}/${id}`);
  }

  getStopsForRoute(routeId: number): Observable<RouteStop[]> {
    return this.http.get<RouteStop[]>(`${this.apiUrl}/${routeId}/stops`);
  }

  addStop(routeId: number, dto: RouteStopDTO): Observable<RouteStop> {
    return this.http.post<RouteStop>(`${this.apiUrl}/${routeId}/stops`, dto);
  }

  removeStop(stopId: number): Observable<{ message: string }> {
    return this.http.delete<{ message: string }>(`${this.apiUrl}/stops/${stopId}`);
  }
}

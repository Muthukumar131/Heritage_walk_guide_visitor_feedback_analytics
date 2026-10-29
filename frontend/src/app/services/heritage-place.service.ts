import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HeritagePlace } from '../models/heritage-place.model';

@Injectable({
  providedIn: 'root'
})
export class HeritagePlaceService {
  private readonly apiUrl = typeof window !== 'undefined' && window.location.port === '4200'
    ? 'http://localhost:8080/api/places'
    : '/api/places';

  constructor(private http: HttpClient) {}

  getAllPlaces(search?: string, category?: string): Observable<HeritagePlace[]> {
    let params = new HttpParams();
    if (search && search.trim() !== '') {
      params = params.set('search', search.trim());
    }
    if (category && category.trim() !== '' && category !== 'ALL') {
      params = params.set('category', category.trim());
    }
    return this.http.get<HeritagePlace[]>(this.apiUrl, { params });
  }

  getPlaceById(id: number): Observable<HeritagePlace> {
    return this.http.get<HeritagePlace>(`${this.apiUrl}/${id}`);
  }

  createPlace(place: HeritagePlace): Observable<HeritagePlace> {
    return this.http.post<HeritagePlace>(this.apiUrl, place);
  }

  updatePlace(id: number, place: HeritagePlace): Observable<HeritagePlace> {
    return this.http.put<HeritagePlace>(`${this.apiUrl}/${id}`, place);
  }

  deletePlace(id: number): Observable<{ message: string }> {
    return this.http.delete<{ message: string }>(`${this.apiUrl}/${id}`);
  }
}

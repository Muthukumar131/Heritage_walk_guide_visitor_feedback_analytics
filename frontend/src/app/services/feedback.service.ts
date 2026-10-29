import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { VisitorFeedback, FeedbackRequestDTO, ReviewModerationSummary } from '../models/feedback.model';

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {
  private readonly baseUrl = typeof window !== 'undefined' && window.location.port === '4200'
    ? 'http://localhost:8080/api'
    : '/api';

  constructor(private http: HttpClient) {}

  // 1. Submit review -> Enforces PENDING status unconditionally
  submitFeedback(dto: FeedbackRequestDTO): Observable<VisitorFeedback> {
    return this.http.post<VisitorFeedback>(`${this.baseUrl}/reviews`, dto);
  }

  // 2. Visitor's personal reviews (all statuses for this user)
  getMyReviews(userId?: number, email?: string): Observable<VisitorFeedback[]> {
    let params = new HttpParams();
    if (userId) params = params.set('userId', userId.toString());
    if (email) params = params.set('email', email);
    return this.http.get<VisitorFeedback[]>(`${this.baseUrl}/reviews/my`, { params });
  }

  // 3. Edit own review -> Resets status to PENDING
  updateFeedback(id: number, userId: number | undefined, dto: Partial<FeedbackRequestDTO>): Observable<VisitorFeedback> {
    let params = new HttpParams();
    if (userId) params = params.set('userId', userId.toString());
    return this.http.put<VisitorFeedback>(`${this.baseUrl}/reviews/${id}`, dto, { params });
  }

  // 4. Delete own review
  deleteFeedback(id: number, userId?: number, role?: string): Observable<{ message: string }> {
    let params = new HttpParams();
    if (userId) params = params.set('userId', userId.toString());
    if (role) params = params.set('role', role);
    return this.http.delete<{ message: string }>(`${this.baseUrl}/reviews/${id}`, { params });
  }

  // 5. Public place reviews: APPROVED only
  getFeedbackForPlace(placeId: number): Observable<VisitorFeedback[]> {
    return this.http.get<VisitorFeedback[]>(`${this.baseUrl}/places/${placeId}/reviews`);
  }

  // 6. Public feed: APPROVED only
  getAllFeedback(): Observable<VisitorFeedback[]> {
    return this.http.get<VisitorFeedback[]>(`${this.baseUrl}/feedback`);
  }

  // 7. Admin Moderation: Retrieve reviews by status and filters
  getAdminReviews(filters: {
    status?: string;
    siteId?: number;
    minRating?: number;
    startDate?: string;
    endDate?: string;
    search?: string;
  }): Observable<VisitorFeedback[]> {
    let params = new HttpParams();
    if (filters.status && filters.status !== 'ALL') params = params.set('status', filters.status);
    if (filters.siteId) params = params.set('siteId', filters.siteId.toString());
    if (filters.minRating) params = params.set('minRating', filters.minRating.toString());
    if (filters.startDate) params = params.set('startDate', filters.startDate);
    if (filters.endDate) params = params.set('endDate', filters.endDate);
    if (filters.search) params = params.set('search', filters.search);
    return this.http.get<VisitorFeedback[]>(`${this.baseUrl}/admin/reviews`, { params });
  }

  // 8. Admin Moderation: Summary counts (Pending, Approved, Rejected, Total)
  getModerationSummary(): Observable<ReviewModerationSummary> {
    return this.http.get<ReviewModerationSummary>(`${this.baseUrl}/admin/reviews/summary`);
  }

  // 9. Admin Action: Approve a review
  approveReview(id: number, moderator: string = 'admin'): Observable<VisitorFeedback> {
    let params = new HttpParams().set('moderator', moderator);
    return this.http.patch<VisitorFeedback>(`${this.baseUrl}/admin/reviews/${id}/approve`, null, { params });
  }

  // 10. Admin Action: Reject a review with reason
  rejectReview(id: number, moderator: string = 'admin', reason?: string): Observable<VisitorFeedback> {
    let params = new HttpParams().set('moderator', moderator);
    if (reason) params = params.set('reason', reason);
    return this.http.patch<VisitorFeedback>(`${this.baseUrl}/admin/reviews/${id}/reject`, { reason, moderator }, { params });
  }

  // Backward compatibility alias
  updateModerationStatus(id: number, status: string): Observable<VisitorFeedback> {
    if (status.toUpperCase() === 'APPROVED') {
      return this.approveReview(id);
    } else if (status.toUpperCase() === 'REJECTED') {
      return this.rejectReview(id, 'admin', 'Rejected by administrator');
    } else {
      let params = new HttpParams().set('status', status);
      return this.http.patch<VisitorFeedback>(`${this.baseUrl}/feedback/${id}/status`, null, { params });
    }
  }

  getFilteredFeedback(filters: any): Observable<VisitorFeedback[]> {
    return this.getAdminReviews(filters);
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface SiteReportSummary {
  siteId: number;
  siteName: string;
  category: string;
  location: string;
  culturalSignificance: string;
  averageRating: number;
  reviewCount: number;
}

export interface NgoReportSummaryDTO {
  reportTitle: string;
  organizationName: string;
  reportingPeriod: string;
  generatedDate: string;
  totalSitesCovered: number;
  totalReviewsAnalyzed: number;
  approvedReviewsCount?: number;
  pendingReviewsCount?: number;
  rejectedReviewsCount?: number;
  moderationComplianceNote?: string;
  overallAverageRating: number;
  ratingDistribution: { [key: number]: number };
  siteSummaries: SiteReportSummary[];
  feedbackCategories: { [key: string]: number };
  observations: string[];
  recommendations: string[];
  methodology: string;
}

export interface ReportFilterParams {
  siteId?: number;
  startDate?: string;
  endDate?: string;
  category?: string;
  minRating?: number;
  organizationName?: string;
  includeReviews?: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class ReportService {
  private readonly apiUrl = typeof window !== 'undefined' && window.location.port === '4200'
    ? 'http://localhost:8080/api/admin/reports'
    : '/api/admin/reports';

  constructor(private http: HttpClient) {}

  private buildParams(filters: ReportFilterParams): HttpParams {
    let params = new HttpParams();
    if (filters.siteId) params = params.set('siteId', filters.siteId.toString());
    if (filters.startDate) params = params.set('startDate', filters.startDate);
    if (filters.endDate) params = params.set('endDate', filters.endDate);
    if (filters.category && filters.category !== 'ALL') params = params.set('category', filters.category);
    if (filters.minRating) params = params.set('minRating', filters.minRating.toString());
    if (filters.organizationName) params = params.set('organizationName', filters.organizationName);
    if (filters.includeReviews !== undefined) params = params.set('includeReviews', filters.includeReviews.toString());
    return params;
  }

  getReportSummary(filters: ReportFilterParams): Observable<NgoReportSummaryDTO> {
    return this.http.get<NgoReportSummaryDTO>(`${this.apiUrl}/summary`, {
      params: this.buildParams(filters)
    });
  }

  downloadPdf(filters: ReportFilterParams): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/pdf`, {
      params: this.buildParams(filters),
      responseType: 'blob'
    });
  }

  downloadExcel(filters: ReportFilterParams): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/excel`, {
      params: this.buildParams(filters),
      responseType: 'blob'
    });
  }
}

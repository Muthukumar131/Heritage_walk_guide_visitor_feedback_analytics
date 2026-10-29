import { HeritagePlace } from './heritage-place.model';

export type ReviewStatus = 'PENDING' | 'APPROVED' | 'REJECTED';

export interface VisitorFeedback {
  id?: number;
  heritagePlace: HeritagePlace;
  visitorName: string;
  visitorEmail?: string;
  rating: number; // 1 to 5
  comment: string;
  visitDate?: string;
  sentiment?: string; // POSITIVE, NEUTRAL, NEGATIVE
  userId?: number;
  status: ReviewStatus;
  moderationStatus?: string; // Kept for backward compatibility
  moderatedBy?: string;
  moderatedAt?: string;
  moderationReason?: string;
  flaggedContent?: boolean;
  flaggedReason?: string;
  createdAt?: string;
  updatedAt?: string;
}

export interface FeedbackRequestDTO {
  heritagePlaceId: number;
  visitorName: string;
  visitorEmail?: string;
  rating: number;
  comment: string;
  visitDate?: string;
  userId?: number;
}

export interface ReviewModerationSummary {
  pendingCount: number;
  approvedCount: number;
  rejectedCount: number;
  totalCount: number;
}

export interface ModerationAction {
  reason?: string;
  moderator?: string;
}

import { HeritagePlace } from './heritage-place.model';
import { VisitorFeedback } from './feedback.model';

export interface VisitorDashboardDTO {
  totalSitesAvailable: number;
  sitesExploredCount: number;
  reviewsSubmittedCount: number;
  averageRatingGiven: number;
  featuredSites: HeritagePlace[];
  recentSites: HeritagePlace[];
  myRecentReviews: VisitorFeedback[];
}

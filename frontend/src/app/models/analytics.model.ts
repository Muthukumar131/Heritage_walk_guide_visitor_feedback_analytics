import { HeritagePlace } from './heritage-place.model';
import { VisitorFeedback } from './feedback.model';

export interface PlaceRatingSummary {
  placeId: number;
  placeName: string;
  averageRating: number;
  reviewCount: number;
}

export interface AnalyticsOverview {
  totalPlaces: number;
  totalRoutes: number;
  totalFeedback: number;
  totalRegisteredVisitors?: number;
  overallAverageRating: number;
  ratingDistribution: { [key: number]: number };
  placeRatings: PlaceRatingSummary[];
  recentFeedback: VisitorFeedback[];
  popularPlaces: HeritagePlace[];
  highestRatedSiteName?: string;
  highestRatedScore?: number;
  lowestRatedSiteName?: string;
  lowestRatedScore?: number;
  mostReviewedSiteName?: string;
  mostReviewedCount?: number;
  leastReviewedSiteName?: string;
  leastReviewedCount?: number;
  reviewTrends?: { [key: string]: number };
}

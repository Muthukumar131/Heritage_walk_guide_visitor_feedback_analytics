import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HeritagePlaceService } from '../../../services/heritage-place.service';
import { FeedbackService } from '../../../services/feedback.service';
import { HeritagePlace } from '../../../models/heritage-place.model';
import { VisitorFeedback } from '../../../models/feedback.model';

@Component({
  selector: 'app-place-detail',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './place-detail.component.html',
  styleUrls: ['./place-detail.component.css']
})
export class PlaceDetailComponent implements OnInit {
  place: HeritagePlace | null = null;
  reviews: VisitorFeedback[] = [];
  averageRating = 0;
  loading = true;
  error = '';

  constructor(
    private route: ActivatedRoute,
    private placeService: HeritagePlaceService,
    private feedbackService: FeedbackService
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      const placeId = Number(idParam);
      this.loadPlaceDetails(placeId);
    } else {
      this.error = 'Invalid monument ID.';
      this.loading = false;
    }
  }

  loadPlaceDetails(id: number): void {
    this.loading = true;
    this.error = '';

    // Fetch place details (increments visit count on backend)
    this.placeService.getPlaceById(id).subscribe({
      next: (place) => {
        this.place = place;
        this.loading = false;
        this.loadReviews(id);
      },
      error: (err) => {
        console.error('Failed to load place details', err);
        this.error = 'Could not load details for this heritage monument.';
        this.loading = false;
      }
    });
  }

  loadReviews(placeId: number): void {
    this.feedbackService.getFeedbackForPlace(placeId).subscribe({
      next: (reviews) => {
        // Enforce approved-only reviews on public heritage site page
        this.reviews = reviews.filter(r => r.status === 'APPROVED' || r.moderationStatus === 'APPROVED');
        if (this.reviews.length > 0) {
          const sum = this.reviews.reduce((acc, curr) => acc + curr.rating, 0);
          this.averageRating = Number((sum / this.reviews.length).toFixed(1));
        } else {
          this.averageRating = 0;
        }
      },
      error: (err) => console.error('Failed to load reviews for place', err)
    });
  }

  getStarArray(rating: number): number[] {
    return Array(Math.round(rating)).fill(0);
  }

  getEmptyStarArray(rating: number): number[] {
    const emptyCount = 5 - Math.round(rating);
    return Array(emptyCount > 0 ? emptyCount : 0).fill(0);
  }
}

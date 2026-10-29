import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { FeedbackService } from '../../services/feedback.service';
import { HeritagePlaceService } from '../../services/heritage-place.service';
import { AuthService } from '../../services/auth.service';
import { HeritagePlace } from '../../models/heritage-place.model';
import { VisitorFeedback, FeedbackRequestDTO } from '../../models/feedback.model';

@Component({
  selector: 'app-feedback',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './feedback.component.html',
  styleUrls: ['./feedback.component.css']
})
export class FeedbackComponent implements OnInit {
  places: HeritagePlace[] = [];
  recentFeedback: VisitorFeedback[] = [];

  // Form Model
  formData: FeedbackRequestDTO = {
    heritagePlaceId: 1,
    visitorName: '',
    visitorEmail: '',
    rating: 5,
    comment: '',
    visitDate: new Date().toISOString().split('T')[0]
  };

  hoveredRating = 0;
  isSubmitting = false;
  successMessage = '';
  errorMessage = '';
  selectedFilterPlaceId: number | 'ALL' = 'ALL';

  constructor(
    private route: ActivatedRoute,
    private feedbackService: FeedbackService,
    private placeService: HeritagePlaceService,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadPlaces();
    this.loadAllFeedback();

    if (this.authService.isAuthenticated()) {
      const user = this.authService.getCurrentUser();
      if (user) {
        this.formData.visitorName = user.fullName || user.username || '';
        this.formData.userId = user.userId;
      }
    }

    // Check query params if pre-selected placeId is present
    this.route.queryParams.subscribe((params) => {
      if (params['placeId']) {
        this.formData.heritagePlaceId = Number(params['placeId']);
      }
    });
  }

  loadPlaces(): void {
    this.placeService.getAllPlaces().subscribe({
      next: (data) => {
        this.places = data;
        if (this.places.length > 0 && !this.formData.heritagePlaceId) {
          this.formData.heritagePlaceId = this.places[0].id!;
        }
      },
      error: (err) => console.error('Failed to load places for feedback dropdown', err)
    });
  }

  loadAllFeedback(): void {
    this.feedbackService.getAllFeedback().subscribe({
      next: (data) => {
        this.recentFeedback = data;
      },
      error: (err) => console.error('Failed to load feedback list', err)
    });
  }

  setRating(rating: number): void {
    this.formData.rating = rating;
  }

  setHover(rating: number): void {
    this.hoveredRating = rating;
  }

  clearHover(): void {
    this.hoveredRating = 0;
  }

  onSubmit(): void {
    this.successMessage = '';
    this.errorMessage = '';

    // Validation
    if (!this.formData.visitorName || this.formData.visitorName.trim() === '') {
      this.errorMessage = 'Please enter your name.';
      return;
    }

    if (!this.formData.comment || this.formData.comment.trim() === '') {
      this.errorMessage = 'Please write a review comment.';
      return;
    }

    if (!this.formData.heritagePlaceId) {
      this.errorMessage = 'Please select a heritage place.';
      return;
    }

    this.isSubmitting = true;

    this.feedbackService.submitFeedback(this.formData).subscribe({
      next: (savedFeedback) => {
        this.isSubmitting = false;
        this.successMessage = 'Your review has been submitted and is awaiting admin approval. You can track its status in your Visitor Dashboard.';
        
        // Refresh approved public reviews from server (pending review will not appear publicly)
        this.loadAllFeedback();

        // Reset form inputs except place
        if (this.authService.isAuthenticated()) {
          const user = this.authService.getCurrentUser();
          this.formData.visitorName = user?.fullName || user?.username || '';
          this.formData.userId = user?.userId;
        } else {
          this.formData.visitorName = '';
          this.formData.visitorEmail = '';
        }
        this.formData.comment = '';
        this.formData.rating = 5;
      },
      error: (err) => {
        this.isSubmitting = false;
        console.error('Failed to submit feedback', err);
        this.errorMessage = 'Failed to submit review. Please ensure the backend is running.';
      }
    });
  }

  get filteredFeedback(): VisitorFeedback[] {
    if (this.selectedFilterPlaceId === 'ALL') {
      return this.recentFeedback;
    }
    return this.recentFeedback.filter(
      (f) => f.heritagePlace && f.heritagePlace.id === Number(this.selectedFilterPlaceId)
    );
  }

  getStarArray(rating: number): number[] {
    return Array(Math.round(rating)).fill(0);
  }

  getEmptyStarArray(rating: number): number[] {
    const empty = 5 - Math.round(rating);
    return Array(empty > 0 ? empty : 0).fill(0);
  }
}

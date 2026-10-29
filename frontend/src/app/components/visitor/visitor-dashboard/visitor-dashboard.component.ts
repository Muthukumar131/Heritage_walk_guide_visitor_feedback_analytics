import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { VisitorService } from '../../../services/visitor.service';
import { FeedbackService } from '../../../services/feedback.service';
import { VisitorDashboardDTO } from '../../../models/visitor-dashboard.model';
import { VisitorFeedback } from '../../../models/feedback.model';

@Component({
  selector: 'app-visitor-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './visitor-dashboard.component.html',
  styleUrls: ['./visitor-dashboard.component.css']
})
export class VisitorDashboardComponent implements OnInit {
  dashboardData: VisitorDashboardDTO | null = null;
  loading = true;
  errorMessage = '';
  successMessage = '';

  // Edit Review Modal state
  editingReview: VisitorFeedback | null = null;
  editRating = 5;
  editComment = '';
  savingEdit = false;

  constructor(
    public authService: AuthService,
    private visitorService: VisitorService,
    private feedbackService: FeedbackService,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (!this.authService.isAuthenticated()) {
      this.router.navigate(['/admin/login']);
      return;
    }
    this.loadDashboard();
  }

  loadDashboard(): void {
    this.loading = true;
    const userId = this.authService.getUserId();
    const currentUser = this.authService.getCurrentUser();

    this.visitorService.getDashboardData(userId, currentUser?.username).subscribe({
      next: (data) => {
        this.dashboardData = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Failed to load visitor dashboard', err);
        this.errorMessage = 'Could not load your dashboard. Please try refreshing.';
        this.loading = false;
      }
    });
  }

  startEditReview(review: VisitorFeedback): void {
    this.editingReview = review;
    this.editRating = review.rating;
    this.editComment = review.comment;
    this.errorMessage = '';
    this.successMessage = '';
  }

  cancelEdit(): void {
    this.editingReview = null;
  }

  saveEditReview(): void {
    if (!this.editingReview || !this.editingReview.id) return;
    if (!this.editComment.trim()) {
      this.errorMessage = 'Review comment cannot be empty.';
      return;
    }

    this.savingEdit = true;
    const userId = this.authService.getUserId();

    this.feedbackService.updateFeedback(this.editingReview.id, userId, {
      rating: this.editRating,
      comment: this.editComment
    }).subscribe({
      next: (updated) => {
        this.savingEdit = false;
        this.successMessage = 'Your review has been updated and resubmitted for admin approval (Status: PENDING).';
        this.editingReview = null;
        this.loadDashboard();
      },
      error: (err) => {
        this.savingEdit = false;
        this.errorMessage = err.error?.message || 'Failed to update review.';
      }
    });
  }

  deleteReview(reviewId?: number): void {
    if (!reviewId) return;
    if (!confirm('Are you sure you want to delete this review? This action cannot be undone.')) {
      return;
    }

    const userId = this.authService.getUserId();
    this.feedbackService.deleteFeedback(reviewId, userId, 'ROLE_VISITOR').subscribe({
      next: () => {
        this.successMessage = 'Review deleted successfully.';
        this.loadDashboard();
      },
      error: (err) => {
        this.errorMessage = err.error?.message || 'Failed to delete review.';
      }
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/home']);
  }
}

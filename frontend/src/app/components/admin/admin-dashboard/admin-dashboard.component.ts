import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { HeritagePlaceService } from '../../../services/heritage-place.service';
import { WalkingRouteService } from '../../../services/walking-route.service';
import { FeedbackService } from '../../../services/feedback.service';
import { ReportService, NgoReportSummaryDTO, ReportFilterParams } from '../../../services/report.service';
import { HeritagePlace } from '../../../models/heritage-place.model';
import { WalkingRoute } from '../../../models/walking-route.model';
import { RouteStop, RouteStopDTO } from '../../../models/route-stop.model';
import { VisitorFeedback, ReviewModerationSummary } from '../../../models/feedback.model';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  activeTab: 'places' | 'routes' | 'feedback' | 'reports' = 'places';

  // Data lists
  places: HeritagePlace[] = [];
  routes: WalkingRoute[] = [];
  feedbackList: VisitorFeedback[] = [];

  // Feedback & alerts
  alertMessage = '';
  alertType: 'success' | 'danger' = 'success';
  loading = false;

  // Place Form (for Add & Edit)
  isEditingPlace = false;
  placeForm: HeritagePlace = this.getEmptyPlace();

  // Route Form (for Add & Edit)
  isEditingRoute = false;
  routeForm: WalkingRoute = this.getEmptyRoute();

  // Route Stops Management Modal/Form
  selectedRouteForStops: WalkingRoute | null = null;
  newStopDTO: RouteStopDTO = {
    heritagePlaceId: 1,
    sequenceOrder: 1,
    stopNotes: ''
  };

  // Review Filtering & Dedicated Moderation
  moderationSummary: ReviewModerationSummary = {
    pendingCount: 0,
    approvedCount: 0,
    rejectedCount: 0,
    totalCount: 0
  };

  reviewFilter = {
    status: 'ALL',
    siteId: undefined as number | undefined,
    minRating: undefined as number | undefined,
    startDate: '',
    endDate: '',
    search: ''
  };

  selectedReviewForReject: VisitorFeedback | null = null;
  rejectReasonText: string = '';
  reviewDetailsModal: VisitorFeedback | null = null;

  // NGO Reports
  reportFilters: ReportFilterParams = {
    siteId: undefined,
    startDate: '',
    endDate: '',
    category: 'ALL',
    minRating: undefined,
    organizationName: 'Heritage Preservation Trust & Cultural Tourism NGO',
    includeReviews: true
  };
  reportSummary: NgoReportSummaryDTO | null = null;
  loadingSummary = false;
  downloadingPdf = false;
  downloadingExcel = false;

  constructor(
    public authService: AuthService,
    private router: Router,
    private placeService: HeritagePlaceService,
    private routeService: WalkingRouteService,
    private feedbackService: FeedbackService,
    private reportService: ReportService
  ) {}

  ngOnInit(): void {
    if (!this.authService.isAuthenticated()) {
      this.router.navigate(['/admin/login']);
      return;
    }
    if (!this.authService.isAdmin()) {
      this.router.navigate(['/visitor']);
      return;
    }
    this.refreshAllData();
  }

  refreshAllData(): void {
    this.loadPlaces();
    this.loadRoutes();
    this.loadFeedback();
    this.loadModerationSummary();
    this.loadReportSummary();
  }

  // ------------------ PLACES CRUD ------------------
  loadPlaces(): void {
    this.placeService.getAllPlaces().subscribe({
      next: (data) => (this.places = data),
      error: (err) => console.error('Failed to load places', err)
    });
  }

  openAddPlace(): void {
    this.isEditingPlace = false;
    this.placeForm = this.getEmptyPlace();
  }

  openEditPlace(place: HeritagePlace): void {
    this.isEditingPlace = true;
    this.placeForm = { ...place };
  }

  savePlace(): void {
    if (!this.placeForm.name || !this.placeForm.description || !this.placeForm.address) {
      this.showAlert('Please fill in all required place fields.', 'danger');
      return;
    }

    if (this.isEditingPlace && this.placeForm.id) {
      this.placeService.updatePlace(this.placeForm.id, this.placeForm).subscribe({
        next: () => {
          this.showAlert('Heritage Place updated successfully!', 'success');
          this.loadPlaces();
          this.placeForm = this.getEmptyPlace();
          this.isEditingPlace = false;
        },
        error: (err) => this.showAlert('Failed to update place.', 'danger')
      });
    } else {
      this.placeService.createPlace(this.placeForm).subscribe({
        next: () => {
          this.showAlert('New Heritage Place added successfully!', 'success');
          this.loadPlaces();
          this.placeForm = this.getEmptyPlace();
        },
        error: (err) => this.showAlert('Failed to create place.', 'danger')
      });
    }
  }

  deletePlace(id?: number): void {
    if (!id) return;
    if (confirm('Are you sure you want to permanently delete this heritage monument?')) {
      this.placeService.deletePlace(id).subscribe({
        next: () => {
          this.showAlert('Monument deleted successfully.', 'success');
          this.loadPlaces();
        },
        error: (err) => this.showAlert('Failed to delete monument.', 'danger')
      });
    }
  }

  // ------------------ ROUTES CRUD ------------------
  loadRoutes(): void {
    this.routeService.getAllRoutes().subscribe({
      next: (data) => (this.routes = data),
      error: (err) => console.error('Failed to load routes', err)
    });
  }

  openAddRoute(): void {
    this.isEditingRoute = false;
    this.routeForm = this.getEmptyRoute();
  }

  openEditRoute(route: WalkingRoute): void {
    this.isEditingRoute = true;
    this.routeForm = { ...route };
  }

  saveRoute(): void {
    if (!this.routeForm.name || !this.routeForm.description || !this.routeForm.startingPoint) {
      this.showAlert('Please fill in all required route fields.', 'danger');
      return;
    }

    if (this.isEditingRoute && this.routeForm.id) {
      this.routeService.updateRoute(this.routeForm.id, this.routeForm).subscribe({
        next: () => {
          this.showAlert('Walking route updated successfully!', 'success');
          this.loadRoutes();
          this.routeForm = this.getEmptyRoute();
          this.isEditingRoute = false;
        },
        error: (err) => this.showAlert('Failed to update route.', 'danger')
      });
    } else {
      this.routeService.createRoute(this.routeForm).subscribe({
        next: () => {
          this.showAlert('New Walking Route created successfully!', 'success');
          this.loadRoutes();
          this.routeForm = this.getEmptyRoute();
        },
        error: (err) => this.showAlert('Failed to create route.', 'danger')
      });
    }
  }

  deleteRoute(id?: number): void {
    if (!id) return;
    if (confirm('Are you sure you want to delete this walking route and its stops?')) {
      this.routeService.deleteRoute(id).subscribe({
        next: () => {
          this.showAlert('Route deleted successfully.', 'success');
          this.loadRoutes();
        },
        error: (err) => this.showAlert('Failed to delete route.', 'danger')
      });
    }
  }

  // ------------------ ROUTE STOPS ------------------
  manageStops(route: WalkingRoute): void {
    this.selectedRouteForStops = route;
    this.newStopDTO = {
      heritagePlaceId: this.places.length > 0 ? this.places[0].id! : 1,
      sequenceOrder: (route.stops?.length || 0) + 1,
      stopNotes: ''
    };
  }

  addStop(): void {
    if (!this.selectedRouteForStops || !this.selectedRouteForStops.id) return;

    this.routeService.addStop(this.selectedRouteForStops.id, this.newStopDTO).subscribe({
      next: () => {
        this.showAlert('Stop added to walking route!', 'success');
        this.loadRoutes();
        this.routeService.getRouteById(this.selectedRouteForStops!.id!).subscribe((r) => {
          this.selectedRouteForStops = r;
          this.newStopDTO.sequenceOrder = (r.stops?.length || 0) + 1;
          this.newStopDTO.stopNotes = '';
        });
      },
      error: (err) => this.showAlert('Failed to add stop.', 'danger')
    });
  }

  removeStop(stopId?: number): void {
    if (!stopId) return;
    this.routeService.removeStop(stopId).subscribe({
      next: () => {
        this.showAlert('Stop removed from route.', 'success');
        this.loadRoutes();
        if (this.selectedRouteForStops?.id) {
          this.routeService.getRouteById(this.selectedRouteForStops.id).subscribe((r) => {
            this.selectedRouteForStops = r;
          });
        }
      },
      error: (err) => this.showAlert('Failed to remove stop.', 'danger')
    });
  }

  // ------------------ REVIEW MODERATION HUB ------------------
  loadFeedback(): void {
    this.feedbackService.getAdminReviews(this.reviewFilter).subscribe({
      next: (data) => (this.feedbackList = data),
      error: (err) => console.error('Failed to load feedback', err)
    });
  }

  loadModerationSummary(): void {
    this.feedbackService.getModerationSummary().subscribe({
      next: (data) => (this.moderationSummary = data),
      error: (err) => console.error('Failed to load moderation summary', err)
    });
  }

  filterByQuickStatus(status: string): void {
    this.reviewFilter.status = status;
    this.applyReviewFilters();
  }

  applyReviewFilters(): void {
    this.loadFeedback();
    this.loadModerationSummary();
  }

  resetReviewFilters(): void {
    this.reviewFilter = {
      status: 'ALL',
      siteId: undefined,
      minRating: undefined,
      startDate: '',
      endDate: '',
      search: ''
    };
    this.applyReviewFilters();
  }

  approveReview(review: VisitorFeedback): void {
    if (!review.id) return;
    const moderator = this.authService.getCurrentUser()?.username || 'admin';

    this.feedbackService.approveReview(review.id, moderator).subscribe({
      next: () => {
        this.showAlert(`Review #${review.id} for "${review.heritagePlace.name}" has been APPROVED and published publicly!`, 'success');
        this.loadFeedback();
        this.loadModerationSummary();
      },
      error: (err) => this.showAlert('Failed to approve review.', 'danger')
    });
  }

  openRejectModal(review: VisitorFeedback): void {
    this.selectedReviewForReject = review;
    this.rejectReasonText = '';
  }

  closeRejectModal(): void {
    this.selectedReviewForReject = null;
  }

  confirmRejectReview(): void {
    if (!this.selectedReviewForReject || !this.selectedReviewForReject.id) return;
    const moderator = this.authService.getCurrentUser()?.username || 'admin';

    this.feedbackService.rejectReview(this.selectedReviewForReject.id, moderator, this.rejectReasonText).subscribe({
      next: () => {
        this.showAlert(`Review #${this.selectedReviewForReject?.id} rejected. It remains in the visitor's history with the rejection notice.`, 'success');
        this.selectedReviewForReject = null;
        this.loadFeedback();
        this.loadModerationSummary();
      },
      error: (err) => this.showAlert('Failed to reject review.', 'danger')
    });
  }

  viewDetails(review: VisitorFeedback): void {
    this.reviewDetailsModal = review;
  }

  closeDetailsModal(): void {
    this.reviewDetailsModal = null;
  }

  deleteReviewAdmin(id?: number): void {
    if (!id) return;
    if (confirm('Permanently delete this visitor review? This cannot be undone.')) {
      this.feedbackService.deleteFeedback(id, undefined, 'ROLE_ADMIN').subscribe({
        next: () => {
          this.showAlert('Review permanently deleted from database.', 'success');
          this.loadFeedback();
          this.loadModerationSummary();
        },
        error: (err) => this.showAlert('Failed to delete review.', 'danger')
      });
    }
  }

  // ------------------ NGO REPORTS ------------------
  loadReportSummary(): void {
    this.loadingSummary = true;
    this.reportService.getReportSummary(this.reportFilters).subscribe({
      next: (data) => {
        this.reportSummary = data;
        this.loadingSummary = false;
      },
      error: (err) => {
        console.error('Failed to load report summary', err);
        this.loadingSummary = false;
      }
    });
  }

  downloadPdfReport(): void {
    this.downloadingPdf = true;
    this.reportService.downloadPdf(this.reportFilters).subscribe({
      next: (blob) => {
        this.downloadingPdf = false;
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `heritage_ngo_report_${new Date().toISOString().slice(0, 10)}.pdf`;
        a.click();
        window.URL.revokeObjectURL(url);
        this.showAlert('NGO PDF Report downloaded successfully!', 'success');
      },
      error: (err) => {
        this.downloadingPdf = false;
        this.showAlert('Failed to generate PDF report.', 'danger');
      }
    });
  }

  downloadExcelReport(): void {
    this.downloadingExcel = true;
    this.reportService.downloadExcel(this.reportFilters).subscribe({
      next: (blob) => {
        this.downloadingExcel = false;
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `heritage_ngo_report_${new Date().toISOString().slice(0, 10)}.xlsx`;
        a.click();
        window.URL.revokeObjectURL(url);
        this.showAlert('NGO Excel Spreadsheet downloaded successfully!', 'success');
      },
      error: (err) => {
        this.downloadingExcel = false;
        this.showAlert('Failed to generate Excel report.', 'danger');
      }
    });
  }

  // ------------------ HELPERS ------------------
  showAlert(msg: string, type: 'success' | 'danger'): void {
    this.alertMessage = msg;
    this.alertType = type;
    setTimeout(() => {
      this.alertMessage = '';
    }, 4000);
  }

  private getEmptyPlace(): HeritagePlace {
    return {
      name: '',
      category: 'Fort & Palace',
      description: '',
      historicalBackground: '',
      historicalSignificance: '',
      yearOrPeriod: '',
      address: '',
      latitude: 28.6139,
      longitude: 77.2090,
      imageUrl: '',
      openingTime: '09:00 AM',
      closingTime: '05:30 PM',
      entryFee: 'INR 50',
      status: 'ACTIVE'
    };
  }

  private getEmptyRoute(): WalkingRoute {
    return {
      name: '',
      description: '',
      distanceKm: 3.0,
      estimatedDuration: '2 Hours',
      difficultyLevel: 'Moderate',
      startingPoint: '',
      endingPoint: '',
      imageUrl: '',
      status: 'ACTIVE'
    };
  }
}

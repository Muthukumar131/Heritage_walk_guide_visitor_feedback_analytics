import { Component, OnInit, OnDestroy, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AnalyticsService } from '../../services/analytics.service';
import { AnalyticsOverview } from '../../models/analytics.model';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

@Component({
  selector: 'app-analytics',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './analytics.component.html',
  styleUrls: ['./analytics.component.css']
})
export class AnalyticsComponent implements OnInit, OnDestroy {
  @ViewChild('distributionChart') distributionCanvas!: ElementRef<HTMLCanvasElement>;
  @ViewChild('placeRatingsChart') placeRatingsCanvas!: ElementRef<HTMLCanvasElement>;

  analytics: AnalyticsOverview | null = null;
  loading = true;
  error = '';

  private distChartInstance: Chart | null = null;
  private placesChartInstance: Chart | null = null;

  constructor(private analyticsService: AnalyticsService) {}

  ngOnInit(): void {
    this.loadAnalytics();
  }

  ngOnDestroy(): void {
    this.destroyCharts();
  }

  loadAnalytics(): void {
    this.loading = true;
    this.error = '';

    this.analyticsService.getAnalyticsOverview().subscribe({
      next: (data) => {
        this.analytics = data;
        this.loading = false;
        // Allow Angular DOM to render canvases then draw charts
        setTimeout(() => this.renderCharts(), 100);
      },
      error: (err) => {
        console.error('Failed to load analytics', err);
        this.error = 'Unable to fetch analytics statistics from backend.';
        this.loading = false;
      }
    });
  }

  private destroyCharts(): void {
    if (this.distChartInstance) {
      this.distChartInstance.destroy();
      this.distChartInstance = null;
    }
    if (this.placesChartInstance) {
      this.placesChartInstance.destroy();
      this.placesChartInstance = null;
    }
  }

  private renderCharts(): void {
    if (!this.analytics) return;
    this.destroyCharts();

    // 1. Star Rating Distribution (Doughnut / Bar)
    if (this.distributionCanvas && this.distributionCanvas.nativeElement) {
      const dist = this.analytics.ratingDistribution || {};
      const labels = ['5 Stars', '4 Stars', '3 Stars', '2 Stars', '1 Star'];
      const counts = [
        dist[5] || 0,
        dist[4] || 0,
        dist[3] || 0,
        dist[2] || 0,
        dist[1] || 0
      ];

      this.distChartInstance = new Chart(this.distributionCanvas.nativeElement, {
        type: 'doughnut',
        data: {
          labels: labels,
          datasets: [{
            data: counts,
            backgroundColor: [
              '#198754', // 5 Star (Success Green)
              '#20c997', // 4 Star (Teal)
              '#ffc107', // 3 Star (Amber)
              '#fd7e14', // 2 Star (Orange)
              '#dc3545'  // 1 Star (Red)
            ],
            borderWidth: 2,
            borderColor: '#ffffff'
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            legend: {
              position: 'bottom',
              labels: {
                boxWidth: 14,
                font: { family: "'Plus Jakarta Sans', sans-serif" }
              }
            }
          }
        }
      });
    }

    // 2. Average Ratings by Heritage Monument
    if (this.placeRatingsCanvas && this.placeRatingsCanvas.nativeElement) {
      const summaries = this.analytics.placeRatings || [];
      const placeLabels = summaries.map((s) => s.placeName);
      const placeScores = summaries.map((s) => s.averageRating);

      this.placesChartInstance = new Chart(this.placeRatingsCanvas.nativeElement, {
        type: 'bar',
        data: {
          labels: placeLabels.length > 0 ? placeLabels : ['Sample Monument'],
          datasets: [{
            label: 'Average Star Rating (out of 5)',
            data: placeScores.length > 0 ? placeScores : [4.5],
            backgroundColor: '#832729',
            borderRadius: 6,
            borderColor: '#5c181a',
            borderWidth: 1
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          scales: {
            y: {
              min: 0,
              max: 5,
              ticks: { stepSize: 1 }
            },
            x: {
              ticks: {
                autoSkip: false,
                maxRotation: 30,
                minRotation: 15,
                font: { size: 11 }
              }
            }
          },
          plugins: {
            legend: {
              display: false
            }
          }
        }
      });
    }
  }

  getStarArray(rating: number): number[] {
    return Array(Math.round(rating)).fill(0);
  }

  getEmptyStarArray(rating: number): number[] {
    const empty = 5 - Math.round(rating);
    return Array(empty > 0 ? empty : 0).fill(0);
  }
}

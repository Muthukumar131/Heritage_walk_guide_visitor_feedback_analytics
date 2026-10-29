import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { HeritagePlaceService } from '../../services/heritage-place.service';
import { WalkingRouteService } from '../../services/walking-route.service';
import { AnalyticsService } from '../../services/analytics.service';
import { HeritagePlace } from '../../models/heritage-place.model';
import { WalkingRoute } from '../../models/walking-route.model';
import { AnalyticsOverview } from '../../models/analytics.model';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  featuredPlaces: HeritagePlace[] = [];
  featuredRoutes: WalkingRoute[] = [];
  analytics: AnalyticsOverview | null = null;
  loading = true;
  error = '';

  constructor(
    private placeService: HeritagePlaceService,
    private routeService: WalkingRouteService,
    private analyticsService: AnalyticsService
  ) {}

  ngOnInit(): void {
    this.loadHomeData();
  }

  loadHomeData(): void {
    this.loading = true;
    this.error = '';

    // Load places
    this.placeService.getAllPlaces().subscribe({
      next: (places) => {
        this.featuredPlaces = places.slice(0, 3);
        this.loading = false;
      },
      error: (err) => {
        console.error('Failed to load places', err);
        this.error = 'Unable to reach backend server. Please verify Spring Boot is running on port 8080.';
        this.loading = false;
      }
    });

    // Load walking routes
    this.routeService.getAllRoutes().subscribe({
      next: (routes) => {
        this.featuredRoutes = routes.slice(0, 2);
      },
      error: (err) => console.error('Failed to load routes', err)
    });

    // Load analytics stats
    this.analyticsService.getAnalyticsOverview().subscribe({
      next: (data) => {
        this.analytics = data;
      },
      error: (err) => console.error('Failed to load analytics', err)
    });
  }
}

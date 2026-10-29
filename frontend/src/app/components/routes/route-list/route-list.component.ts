import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { WalkingRouteService } from '../../../services/walking-route.service';
import { WalkingRoute } from '../../../models/walking-route.model';

@Component({
  selector: 'app-route-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './route-list.component.html',
  styleUrls: ['./route-list.component.css']
})
export class RouteListComponent implements OnInit {
  routes: WalkingRoute[] = [];
  loading = true;
  error = '';

  constructor(private routeService: WalkingRouteService) {}

  ngOnInit(): void {
    this.fetchRoutes();
  }

  fetchRoutes(): void {
    this.loading = true;
    this.error = '';

    this.routeService.getAllRoutes().subscribe({
      next: (data) => {
        this.routes = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Failed to load walking routes', err);
        this.error = 'Unable to fetch walking routes from backend API.';
        this.loading = false;
      }
    });
  }
}

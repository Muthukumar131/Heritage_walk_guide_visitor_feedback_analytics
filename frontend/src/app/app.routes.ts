import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { PlaceListComponent } from './components/places/place-list/place-list.component';
import { PlaceDetailComponent } from './components/places/place-detail/place-detail.component';
import { RouteListComponent } from './components/routes/route-list/route-list.component';
import { FeedbackComponent } from './components/feedback/feedback.component';
import { AnalyticsComponent } from './components/analytics/analytics.component';
import { AdminLoginComponent } from './components/admin/admin-login/admin-login.component';
import { AdminDashboardComponent } from './components/admin/admin-dashboard/admin-dashboard.component';
import { VisitorDashboardComponent } from './components/visitor/visitor-dashboard/visitor-dashboard.component';
import { VisitorRegisterComponent } from './components/visitor/visitor-register/visitor-register.component';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'places', component: PlaceListComponent },
  { path: 'places/:id', component: PlaceDetailComponent },
  { path: 'routes', component: RouteListComponent },
  { path: 'feedback', component: FeedbackComponent },
  { path: 'analytics', component: AnalyticsComponent },
  { path: 'admin/login', component: AdminLoginComponent },
  { path: 'admin', component: AdminDashboardComponent },
  { path: 'visitor', component: VisitorDashboardComponent },
  { path: 'register', component: VisitorRegisterComponent },
  { path: '**', redirectTo: 'home' }
];

import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { LoginRequest } from '../../../models/admin-user.model';

@Component({
  selector: 'app-admin-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './admin-login.component.html',
  styleUrls: ['./admin-login.component.css']
})
export class AdminLoginComponent {
  credentials: LoginRequest = {
    username: '',
    password: ''
  };

  loading = false;
  errorMessage = '';

  constructor(private authService: AuthService, private router: Router) {
    if (this.authService.isAuthenticated()) {
      if (this.authService.isAdmin()) {
        this.router.navigate(['/admin']);
      } else {
        this.router.navigate(['/visitor']);
      }
    }
  }

  fillAdmin(): void {
    this.credentials.username = 'admin';
    this.credentials.password = 'admin123';
  }

  fillVisitor(): void {
    this.credentials.username = 'visitor1';
    this.credentials.password = 'visitor123';
  }

  onSubmit(): void {
    this.errorMessage = '';
    if (!this.credentials.username || !this.credentials.password) {
      this.errorMessage = 'Please enter both username and password.';
      return;
    }

    this.loading = true;

    this.authService.login(this.credentials).subscribe({
      next: (response) => {
        this.loading = false;
        if (response.authenticated) {
          if (response.role === 'ROLE_ADMIN') {
            this.router.navigate(['/admin']);
          } else {
            this.router.navigate(['/visitor']);
          }
        } else {
          this.errorMessage = response.message || 'Invalid username or password.';
        }
      },
      error: (err) => {
        this.loading = false;
        console.error('Login error', err);
        this.errorMessage = 'Authentication failed. Please verify Spring Boot backend is running.';
      }
    });
  }
}

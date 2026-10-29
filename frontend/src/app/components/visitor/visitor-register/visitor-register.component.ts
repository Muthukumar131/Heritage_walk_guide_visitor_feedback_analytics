import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { RegisterRequest } from '../../../models/admin-user.model';

@Component({
  selector: 'app-visitor-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './visitor-register.component.html',
  styleUrls: ['./visitor-register.component.css']
})
export class VisitorRegisterComponent {
  user: RegisterRequest = {
    username: '',
    password: '',
    fullName: '',
    email: ''
  };

  confirmPassword = '';
  loading = false;
  errorMessage = '';
  successMessage = '';

  constructor(private authService: AuthService, private router: Router) {
    if (this.authService.isAuthenticated()) {
      if (this.authService.isAdmin()) {
        this.router.navigate(['/admin']);
      } else {
        this.router.navigate(['/visitor']);
      }
    }
  }

  onSubmit(): void {
    this.errorMessage = '';
    this.successMessage = '';

    if (!this.user.fullName || !this.user.username || !this.user.email || !this.user.password) {
      this.errorMessage = 'Please complete all required fields.';
      return;
    }

    if (this.user.password.length < 6) {
      this.errorMessage = 'Password must be at least 6 characters.';
      return;
    }

    if (this.user.password !== this.confirmPassword) {
      this.errorMessage = 'Passwords do not match.';
      return;
    }

    this.loading = true;

    this.authService.register(this.user).subscribe({
      next: (res) => {
        this.loading = false;
        if (res.authenticated) {
          this.successMessage = 'Registration successful! Welcome to the Heritage Portal.';
          setTimeout(() => {
            this.router.navigate(['/visitor']);
          }, 1000);
        } else {
          this.errorMessage = res.message || 'Registration failed.';
        }
      },
      error: (err) => {
        this.loading = false;
        if (err.error && err.error.message) {
          this.errorMessage = err.error.message;
        } else {
          this.errorMessage = 'Registration failed. The username or email might already be registered.';
        }
      }
    });
  }
}

// src/app/register/register.component.ts

import { Component } from '@angular/core';
import { AuthService, RegisterResponse } from '../services/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class RegisterComponent {
  username = '';
  password = '';
  confirmPassword = '';
  errorMessage = '';
  successMessage = '';

  constructor(private authService: AuthService, private router: Router) {}

  register(): void {
    if (this.password !== this.confirmPassword) {
      this.errorMessage = 'Passwords do not match.';
      this.successMessage = '';
      alert("Passwords don't match");
      return;
    }

    this.authService.register({ username: this.username, password: this.password }).subscribe({
      next: (res: RegisterResponse) => {
        if (res.success) {
          this.successMessage = res.message;
          this.errorMessage = '';
          // Optionally, navigate to login
          this.router.navigate(['/login']);
        } else {
          this.errorMessage = res.message || 'Registration failed.';
          this.successMessage = '';
        }
      },
      error: (err) => {
        this.errorMessage = err.error.message || 'Registration failed.';
        this.successMessage = '';
        console.error('Registration error:', err);
      },
    });
  }

  navigateToLogin(): void {
    this.router.navigate(['/login']);
  } 
}


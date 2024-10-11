// src/app/login/login.component.ts

import { Component } from '@angular/core';
import { AuthService, LoginResponse } from '../services/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class LoginComponent {
  username = '';
  password = '';
  errorMessage = '';

  constructor(private authService: AuthService, private router: Router) {}

  login(): void {
    this.authService.login(this.username, this.password).subscribe({
      next: (res: LoginResponse) => {
        console.log('Received token:', res.token);
        console.log('User role:', res.role);

        if (res.token && res.token.split('.').length === 3) {
          localStorage.setItem('authToken', res.token);
          if (res.role) {
            localStorage.setItem('userRole', res.role);
          }

          if (res.role === 'ADMIN') {
            this.router.navigate(['/admin-dashboard']);
          } else if (res.role === 'USER') {
            this.router.navigate(['/user-dashboard']);
          } else {
            this.errorMessage = 'Unknown role.';
            console.error('Unknown role:', res.role);
          }
        } else {
          this.errorMessage = res.message || 'Invalid token received.';
          console.error('Invalid token:', res.token);
        }
      },
      error: (err) => {
        this.errorMessage = 'Invalid username or password.';
        console.error('Login error:', err);
      },
    });
  }

  navigateToRegister(): void {
    this.router.navigate(['/login']);
  }
}

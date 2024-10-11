// src/app/app.routes.ts

import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AdminDashboardComponent } from './dashboards/admin-dashboard/admin-dashboard.component';
import { UserDashboardComponent } from './dashboards/user-dashboard/user-dashboard.component';
import { NotAuthorizedComponent } from './not-authorized/not-authorized.component';
import { RoleGuard } from './guards/role.guard';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },

  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  // Admin Dashboard Route
  { 
    path: 'admin-dashboard', 
    component: AdminDashboardComponent,
    canActivate: [RoleGuard],
    data: { expectedRole: 'ADMIN' }
  },

  // User Dashboard Route
  { 
    path: 'user-dashboard', 
    component: UserDashboardComponent,
    canActivate: [RoleGuard],
    data: { expectedRole: 'USER' }
  },

  // Not Authorized Route
  { path: 'not-authorized', component: NotAuthorizedComponent },

  // Wildcard Route for 404
  { path: '**', redirectTo: '/login' }
];

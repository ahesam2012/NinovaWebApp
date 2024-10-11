// src/app/guards/role.guard.ts

import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';


@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(
    private authService: AuthService,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
    
    if (!this.authService.isAuthenticated()) {
      this.router.navigate(['/login']);
      return false;
    }

    const expectedRole = route.data['expectedRole'];
    const userRole = this.authService.getUserRole();

    if (expectedRole && userRole !== expectedRole) {
      // Optionally, navigate to a 'not authorized' page
      this.router.navigate(['/not-authorized']);
      return false;
    }

    return true;
  }
}

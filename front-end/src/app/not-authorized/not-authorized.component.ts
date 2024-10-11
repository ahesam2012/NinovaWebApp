// src/app/not-authorized/not-authorized.component.ts

import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-not-authorized',
  templateUrl: './not-authorized.component.html',
  styleUrls: ['./not-authorized.component.css'],
  standalone: true,
  imports: [CommonModule]
})
export class NotAuthorizedComponent { 
  constructor(private router: Router) {}

  goHome(): void {
    this.router.navigate(['/']);
  }
}

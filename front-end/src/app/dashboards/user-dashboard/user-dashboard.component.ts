// src/app/dashboards/user-dashboard/user-dashboard.component.ts

import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { OrderService } from '../../services/order.service'; // Assuming you have an OrderService
import { Order } from '../../services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css'],
  standalone: true,
  imports: [CommonModule], // Add CommonModule to imports
})
export class UserDashboardComponent implements OnInit {
  orders: Order[] = [];

  constructor(private authService: AuthService, private orderService: OrderService) { }

  ngOnInit(): void {
    this.loadUserOrders();
  }

  loadUserOrders(): void {
    this.orderService.getUserOrders().subscribe({
      next: (res) => {
        if (res.success && res.orders) {
          this.orders = res.orders;
        }
      },
      error: (err) => {
        console.error('Error fetching user orders:', err);
      }
    });
  }

}

// src/app/services/auth.service.ts

import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { isPlatformBrowser } from '@angular/common';

export interface LoginResponse {
  status: string;
  code: number;
  message: string;
  token?: string;
  role?: string;
  timestamp: string;
  success: boolean;
}

export interface RegisterResponse {
  status: string;
  code: number;
  message: string;
  timestamp: string;
  success: boolean;
}

export interface CreateOrderResponse {
  status: string;
  code: number;
  message: string;
  order: Order;
  timestamp: string;
  success: boolean;
}

export interface GetAllOrdersResponse {
  status: string;
  code: number;
  message: string;
  orders: Order[];
  timestamp: string;
  success: boolean;
}

export interface GetUserOrdersResponse {
  status: string;
  code: number;
  message: string;
  orders: Order[];
  timestamp: string;
  success: boolean;
}

export interface Order {
  // Define the Order properties based on your backend model
  id: number;
  user: any;
  orderStatus: string;
  orderSubtotal?: number;
  // Add other fields as necessary
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';
  private ordersApiUrl = 'http://localhost:8080/api/orders';

  constructor(private http: HttpClient, @Inject(PLATFORM_ID) public platformId: Object) { }

  // Login method
  login(username: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, { username, password });
  }

  // Register method
  register(user: any): Observable<RegisterResponse> {
    return this.http.post<RegisterResponse>(`${this.apiUrl}/register`, user);
  }

  // Create Order
  createOrder(order: Order): Observable<CreateOrderResponse> {
    return this.http.post<CreateOrderResponse>(`${this.ordersApiUrl}/create`, order);
  }

  // Get All Orders
  getAllOrders(): Observable<GetAllOrdersResponse> {
    return this.http.get<GetAllOrdersResponse>(`${this.ordersApiUrl}/all`);
  }

  // Get User Orders
  getUserOrders(): Observable<GetUserOrdersResponse> {
    return this.http.get<GetUserOrdersResponse>(`${this.ordersApiUrl}/user-orders`);
  }

  // Logout method
  logout(): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem('authToken');
      localStorage.removeItem('userRole');
    }
  }

  // Check if user is authenticated
  isAuthenticated(): boolean {
    if (isPlatformBrowser(this.platformId)) {
      return !!localStorage.getItem('authToken');
    }
    return false;
  }

  getUserRole(): string | null {
    if (isPlatformBrowser(this.platformId)) {
      return localStorage.getItem('userRole');
    }
    return null;
  }
}

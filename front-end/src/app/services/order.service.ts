// src/app/services/order.service.ts

import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService, GetUserOrdersResponse, GetAllOrdersResponse, CreateOrderResponse, Order } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private ordersApiUrl = 'http://localhost:8080/api/orders';

  constructor(private http: HttpClient, @Inject(PLATFORM_ID) public platformId: Object) { }

  // Create Order
  createOrder(order: Order): Observable<CreateOrderResponse> {
    return this.http.post<CreateOrderResponse>(`${this.ordersApiUrl}/create`, order);
  }

  // Get All Orders (for Admin)
  getAllOrders(): Observable<GetAllOrdersResponse> {
    return this.http.get<GetAllOrdersResponse>(`${this.ordersApiUrl}/all`);
  }

  // Get User Orders
  getUserOrders(): Observable<GetUserOrdersResponse> {
    const token = localStorage.getItem('authToken');
    console.log(token);
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}` // Include the Bearer token
    });
    return this.http.get<GetUserOrdersResponse>(`${this.ordersApiUrl}/user-orders`, { headers: headers});
  }
}

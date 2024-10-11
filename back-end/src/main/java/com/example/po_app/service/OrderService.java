package com.example.po_app.service;

import com.example.po_app.model.Order;
import com.example.po_app.model.User;
import com.example.po_app.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Create a new order
    public Order createOrder(Order order, User user) {
        order.setUser(user);
        order.setOrderStatus("PENDING");
        return orderRepository.save(order);
    }

    // Get orders by status
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByOrderStatus(status);
    }

    // Get orders by user
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // Update order status
    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setOrderStatus(status);
            return orderRepository.save(order);
        }
        return null;
    }

    // Create a new order
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    // Get order by ID
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }
    
}

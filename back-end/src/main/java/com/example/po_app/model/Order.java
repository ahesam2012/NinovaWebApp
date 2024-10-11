package com.example.po_app.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, columnDefinition="TEXT")
    private String items;

    @Column(name="order_subtotal", nullable=false)
    private BigDecimal orderSubtotal;

    @Column(name="order_status", nullable=false)
    private String orderStatus; // "PENDING", "APPROVED", "REJECTED"

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Constructors


    public Order() {
        this.createdAt = LocalDateTime.now();
    }

    public Order(String items, BigDecimal orderSubtotal, String orderStatus, User user) {
        this.items = items;
        this.orderSubtotal = orderSubtotal;
        this.orderStatus = orderStatus;
        this.user = user;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    // No setter for id since it's auto-generated

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public BigDecimal getOrderSubtotal() {
        return orderSubtotal;
    }

    public void setOrderSubtotal(BigDecimal orderSubtotal) {
        this.orderSubtotal = orderSubtotal;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public User getUser() {
        return user;
    }

    // Set the user when creating the order
    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
}

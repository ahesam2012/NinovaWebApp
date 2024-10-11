// src/main/java/com/example/po_app/model/CreateOrderResponse.java

package com.example.po_app.response;

import com.example.po_app.model.Order;

import java.time.LocalDateTime;

public class CreateOrderResponse {
    private String status;
    private int code;
    private String message;
    private Order order;
    private LocalDateTime timestamp;
    private boolean success;

    // Constructors
    public CreateOrderResponse() {}

    public CreateOrderResponse(String status, int code, String message, Order order, LocalDateTime timestamp, boolean success) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.order = order;
        this.timestamp = timestamp;
        this.success = success;
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

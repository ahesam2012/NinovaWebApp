// src/main/java/com/example/po_app/model/GetUserOrdersResponse.java

package com.example.po_app.response;

import com.example.po_app.model.Order;

import java.time.LocalDateTime;
import java.util.List;

public class GetUserOrdersResponse {
    private String status;
    private int code;
    private String message;
    private List<Order> orders;
    private LocalDateTime timestamp;
    private boolean success;

    // Constructors
    public GetUserOrdersResponse() {}

    public GetUserOrdersResponse(String status, int code, String message, List<Order> orders, LocalDateTime timestamp, boolean success) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.orders = orders;
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
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

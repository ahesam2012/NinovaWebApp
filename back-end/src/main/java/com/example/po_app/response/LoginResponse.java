// src/main/java/com/example/po_app/model/LoginResponse.java

package com.example.po_app.response;

import java.time.LocalDateTime;

public class LoginResponse {
    private String status;
    private int code;
    private String message;
    private String token;
    private String role;
    private LocalDateTime timestamp;
    private boolean success;

    // Constructors
    public LoginResponse() {}

    public LoginResponse(String status, int code, String message, String token, String role, LocalDateTime timestamp, boolean success) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.token = token;
        this.role = role;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

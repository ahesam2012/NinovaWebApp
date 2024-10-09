package com.example.po_app.response;

import java.time.LocalDateTime;

public class CustomResponse<T> {
    private String response_status;
    private int response_code;
    private String error_type;
    private String stack_trace;
    private LocalDateTime timestamp;
    private T data;
    private Boolean success;

    public CustomResponse(String response_status, int response_code, String error_type, String stack_trace, LocalDateTime timestamp, T data, Boolean success) {
        this.response_status = response_status;
        this.response_code = response_code;
        this.error_type = error_type;
        this.stack_trace = stack_trace;
        this.timestamp = timestamp;
        this.data = data;
        this.success = success;
    }

    // Getters and setters
}

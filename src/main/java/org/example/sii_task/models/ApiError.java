package org.example.sii_task.models;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiError {
    private String message;
    private int status;
    private String error;
    private LocalDateTime timestamp;

    public ApiError(String message, HttpStatus status) {
        this.message = message;
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
package org.example.sii_task.exception;

public class BadCurrencyException extends RuntimeException{
    public BadCurrencyException(String message) {
        super(message);
    }
}

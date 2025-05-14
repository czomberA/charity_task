package org.example.sii_task.errorHandling;

public class BadCurrencyException extends RuntimeException{
    public BadCurrencyException(String message) {
        super(message);
    }
}

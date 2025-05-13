package org.example.sii_task.errorHandling;

public class BadCurrency extends RuntimeException{
    public BadCurrency(String message) {
        super(message);
    }
}

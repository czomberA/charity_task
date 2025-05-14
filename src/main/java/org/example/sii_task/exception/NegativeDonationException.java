package org.example.sii_task.exception;

public class NegativeDonationException extends RuntimeException {
    public NegativeDonationException(String message) {
        super(message);
    }
}

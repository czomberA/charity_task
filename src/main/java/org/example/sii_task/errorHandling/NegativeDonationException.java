package org.example.sii_task.errorHandling;

public class NegativeDonationException extends RuntimeException {
    public NegativeDonationException(String message) {
        super(message);
    }
}

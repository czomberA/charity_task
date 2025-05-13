package org.example.sii_task.errorHandling;

public class NotAssigned extends RuntimeException {
    public NotAssigned(String message) {
        super(message);
    }
}

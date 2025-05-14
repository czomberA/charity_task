package org.example.sii_task.errorHandling;

public class NotAssignedException extends RuntimeException {
    public NotAssignedException(String message) {
        super(message);
    }
}

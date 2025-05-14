package org.example.sii_task.exception;

public class DoesNotExistException extends RuntimeException {
    public DoesNotExistException(String message) {
        super(message);
    }
}

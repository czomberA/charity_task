package org.example.sii_task.exception;

public class AlreadyAssignedException extends RuntimeException {
    public AlreadyAssignedException(String message) {
        super(message);
    }
}

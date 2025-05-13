package org.example.sii_task.errorHandling;

public class FundraiserDoesNotExist extends RuntimeException {
    public FundraiserDoesNotExist(String message) {
        super(message);
    }
}

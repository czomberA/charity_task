package org.example.sii_task.errorHandling;

public class AlreadyAssigned extends RuntimeException {
  public AlreadyAssigned(String message) {
    super(message);
  }
}

package org.example.sii_task.errorHandling;

public class NotEmpty extends RuntimeException {
  public NotEmpty(String message) {
    super(message);
  }
}

package com.app.models.exceptions;

/** Exception to be thrown when a Member is not found during lookup */
public class MemberNotFoundException extends Exception {
  public MemberNotFoundException(String message) {
    super(message);
  }
}

package com.app.models;

/** Exception to be thrown when a Member is not found during lookup */
public class MemberNotFoundException extends Exception {

  public MemberNotFoundException() {
    super();
  }

  public MemberNotFoundException(String message) {
    super(message);
  }
}

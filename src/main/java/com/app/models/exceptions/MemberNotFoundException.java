package com.app.models.exceptions;

/** Exception to be thrown when a Member is not found during lookup */
public class MemberNotFoundException extends Exception {

  public MemberNotFoundException() {
    super();
  }

  public MemberNotFoundException(
      String
          message) { // TODO: Constructor 'MemberNotFoundException(java.lang.String)' is never used
    super(message);
  }
}

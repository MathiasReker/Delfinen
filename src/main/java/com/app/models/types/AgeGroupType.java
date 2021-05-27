package com.app.models.types;

/** @auther Mathias */
public enum AgeGroupType {
  JUNIOR(18),
  SENIOR(60);

  private final int AGE;

  AgeGroupType(int age) {
    this.AGE = age;
  }

  public int getAge() {
    return AGE;
  }
}

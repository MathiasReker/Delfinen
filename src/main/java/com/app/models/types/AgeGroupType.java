package com.app.models.types;

public enum AgeGroupType {
  JUNIOR(18),
  SENIOR(60);

  private final int AGE_LIMIT;

  AgeGroupType(int age) {
    this.AGE_LIMIT = age;
  }

  public int getAgeLimit() {
    return AGE_LIMIT;
  }
}

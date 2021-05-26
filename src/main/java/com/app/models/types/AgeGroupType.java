package com.app.models.types;

public enum AgeGroupType {
  JUNIOR(18, 1000),
  SENIOR(60, 1600);

  private final int PRICE; // price in DKK
  private final int AGE;

  AgeGroupType(int age, int price) {
    this.AGE = age;
    this.PRICE = price;
  }

  public int getAge() {
    return AGE;
  }

  public int getPrice() {
    return PRICE * 100; // price in øre
  }
}

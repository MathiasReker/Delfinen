package com.app.models;

public enum Distance {

  FIFTY(50), HUNDRED(100), TWO_HUNDRED(200),
  FOUR_HUNDRED(400), EIGHT_HUNDRED(800), FIFTEEN_HUNDRED(1500);

  private int meters;
  private Distance(int meters) {
    this.meters = meters;
  }

}

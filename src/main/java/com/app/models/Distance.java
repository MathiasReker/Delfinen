package com.app.models;

public enum Distance {

  /**
   * Possible distances for ever swim style.
   */
  FIFTY(50), HUNDRED(100), TWO_HUNDRED(200),
  FOUR_HUNDRED(400), EIGHT_HUNDRED(800), FIFTEEN_HUNDRED(1500);

  private int meters;

  /**
   *
   * @param meters for ever distance we have.
   */
  private Distance(int meters) {
    this.meters = meters;
  }

}

package com.app.models.types;

public enum DistanceType {
  FIFTY(50),
  HUNDRED(100),
  TWO_HUNDRED(200),
  FOUR_HUNDRED(400),
  EIGHT_HUNDRED(800),
  FIFTEEN_HUNDRED(1500);

  private final int METERS;

  /** @param meters for ever distance we have. */
  DistanceType(int meters) {
    this.METERS = meters;
  }

  public int getMeters() {
    return METERS;
  }
}

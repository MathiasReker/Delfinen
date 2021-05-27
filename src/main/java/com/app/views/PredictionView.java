package com.app.views;

import java.math.BigDecimal;

public class PredictionView extends View {
  private float movePointLeft(int prediction) {
    return new BigDecimal(prediction).movePointLeft(2).floatValue();
  }

  /**
   * Print expected income.
   *
   * @param prediction int
   * @param days int
   * @auther Andreas
   */
  public void printExpectedIncome(int prediction, int days) {
    String dayOrDays = days == 1 ? "day" : "days";
    print("Expected income in " + days + " " + dayOrDays);
    System.out.printf("%.2f kr.", movePointLeft(prediction));
  }
}

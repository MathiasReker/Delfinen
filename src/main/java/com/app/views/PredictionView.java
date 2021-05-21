package com.app.views;

public class PredictionView extends View {

  /**
   * Returns a string in a danish currency format
   *
   * @param prediction predicted price to format
   * @return
   */
  private String formatPrediction(int prediction) {
    String result;
    result = String.valueOf(prediction);
    if (result.length() < 2) {
      result = "0,0" + result;
    } else if (result.length() < 3) {
      result = "0," + result;
    } else {
      result =
          result.substring(0, result.length() - 2) + "," + result.substring(result.length() - 2);
    }
    return result;
  }

  public void printExpectedIncome(int prediction, int days) {
    String dayOrDays = days == 1 ? "day" : "days";
    print("Expected income in " + days + " " + dayOrDays);
    print(formatPrediction(prediction) + "kr");
  }
}

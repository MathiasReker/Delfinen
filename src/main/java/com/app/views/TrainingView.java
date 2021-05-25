package com.app.views;

public class TrainingView extends View {

  private final String FORMAT = "%-20s %-15s %-15s %-20s";

  public void displayTrainings(String[][] competitionResults) {
    System.out.println(
        "-----------------------------------------------------------------------------");
    System.out.printf(FORMAT, "Name", "Style", "Distance", "Completion time");
    System.out.println();
    System.out.println(
        "-----------------------------------------------------------------------------");
    for (String[] c : competitionResults) {
      System.out.format(FORMAT, c[0], c[1], c[2] + " m", c[3]);
      System.out.println();
    }
    System.out.println(
        "-----------------------------------------------------------------------------");
  }

}

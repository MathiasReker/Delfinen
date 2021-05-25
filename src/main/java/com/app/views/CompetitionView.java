package com.app.views;

public class CompetitionView extends View {
  public void displayCompetitionResults(String[][] competitionResults) {
    System.out.println(
        "-----------------------------------------------------------------------------");
    System.out.printf("%-10s %-20s %-20s %-20s", "Name", "Style", "Distance", "Completion time");
    System.out.println();
    System.out.println(
        "-----------------------------------------------------------------------------");
    for (String[] c : competitionResults) {
      System.out.format("%-10s %-20s %-20s %-20s", c[0], c[1], c[2] + " m", c[3]);
      System.out.println();
    }
    System.out.println(
        "-----------------------------------------------------------------------------");
  }

  public void displayTopResults(String[][] competitionResults) {
    String format = "%-20s %-10s %-20s %-20s";
    System.out.println(
        "-----------------------------------------------------------------------------");
    System.out.printf(format, "Competition Name", "Placement", "Name", "Completion time");
    System.out.println();
    System.out.println(
        "-----------------------------------------------------------------------------");
    for (String[] c : competitionResults) {
      System.out.format(format, c[0], c[1], c[2], c[3]);
      System.out.println();
    }
    System.out.println(
        "-----------------------------------------------------------------------------");
  }

  public void displayMember(String[] competition, int[] columnWidth) {
    for (int i = 0; i < competition.length; i++) {
      String s = competition[i];

      if (s == null) {
        s = "--";
      }

      System.out.printf("%-" + (columnWidth[i] + 4) + "s", s);
    }

    System.out.println();
  }
}

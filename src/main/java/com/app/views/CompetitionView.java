package com.app.views;

public class CompetitionView extends View {

  public void displayMenu(String[] strings) {
    String[] result = new String[strings.length];

    for (int i = 0; i < strings.length; i++) {
      result[i] = strings[i] + " [" + (i + 1) + "]";
    }

    printInline(String.join(", ", result) + ": ");
  }

  public void displayCompetitionResults(String [] competitionResults){

    System.out.println("-----------------------------------------------------------------------------");
    System.out.printf("%-10s %-20s %-20s %-20s", "Name", "Style", "Distance", "Completion time");
    System.out.println();
    System.out.println("-----------------------------------------------------------------------------");
    for (int i = 0; i < competitionResults.length ; i++) {
      String [] temp = competitionResults[i].split(";");
      System.out.format("%-10s %-20s %-20s %-10s",
          temp[0], temp[1], temp[2],temp[3]);
      System.out.println();
    }
    System.out.println("-----------------------------------------------------------------------------");
  }
}

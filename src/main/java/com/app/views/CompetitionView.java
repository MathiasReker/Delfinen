package com.app.views;

public class CompetitionView extends View {

  public void displayMenu(String[] strings) {
    String[] result = new String[strings.length];

    for (int i = 0; i < strings.length; i++) {
      result[i] = strings[i] + " [" + (i + 1) + "]";
    }

    printInline(String.join(", ", result) + ": ");
  }
}

package com.app.views;

public class PaymentsView extends View {
  public void displayArrears(String[][] arrears) {
    System.out.println("-----------------------");
    System.out.printf("%-10s %-20s %-30s", "ID", "Name", "days");
    System.out.println();
    System.out.println("-----------------------");
    for (String[] string : arrears) {
      if (null == string[1]) {
        string[1] = "--";
      }
      System.out.format("%-10s %-20s %-30s", string[0], string[1], string[2]);
      System.out.println();
    }
    System.out.println("-----------------------");
  }
}

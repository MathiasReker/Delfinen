package com.app.views;

public class PaymentsView extends View {
  public void displayPayments(String[][] payments) {
    System.out.println("-----------------------");
    System.out.printf("%-10s %-20s", "ID", "Name");
    System.out.println();
    System.out.println("-----------------------");
    for (String[] payment : payments) {
      if (null == payment[1]) {
        payment[1] = "--";
      }
      System.out.format("%-10s %-20s", payment[0], payment[1]);
      System.out.println();
    }
    System.out.println("-----------------------");
  }
}

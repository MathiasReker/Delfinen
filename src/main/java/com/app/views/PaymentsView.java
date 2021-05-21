package com.app.views;

public class PaymentsView extends View{

  public void displayPayments(String[] payments) {

    System.out.println(
        "-----------------------");
    System.out.printf("%-10s %-20s", "ID", "Name");
    System.out.println();
    System.out.println(
        "-----------------------");
    for (int i = 0; i < payments.length; i++) {
      String[] temp = payments[i].split(";");
      System.out.format("%-10s %-20s", temp[0], temp[1]);
      System.out.println();
    }
    System.out.println(
        "-----------------------");
  }
}

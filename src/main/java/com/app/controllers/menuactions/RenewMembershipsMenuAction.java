package com.app.controllers.menuactions;

import com.app.controllers.PaymentController;

import java.util.Scanner;

public class RenewMembershipsMenuAction extends MenuActions {

  public RenewMembershipsMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    Scanner in = new Scanner(System.in);
    new PaymentController().handlePayments(in);
  }
}

package com.app.controllers.menuactions;

import com.app.controllers.PaymentController;


public class RenewMembershipsMenuAction extends MenuActions {

  public RenewMembershipsMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    new PaymentController().handlePayments();
  }
}

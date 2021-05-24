package com.app.controllers.menuactions;

import com.app.controllers.PaymentController;

public class HandlePaymentMenuAction extends MenuActions {

  public HandlePaymentMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    new PaymentController().handlePayments();
  }
}

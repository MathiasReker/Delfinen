package com.app.controllers.menuactions;

import com.app.controllers.PaymentController;

public class PaymentRequestMenuAction extends MenuAction {
  public PaymentRequestMenuAction(String description) {
    super(description);
  }

  @Override
  public void run() {
    new PaymentController().requestPaymentForUnpaidMembers();
  }
}

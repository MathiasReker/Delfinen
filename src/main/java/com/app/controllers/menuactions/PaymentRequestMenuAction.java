package com.app.controllers.menuactions;

import com.app.controllers.MemberController;

public class PaymentRequestMenuAction extends MenuAction {
  public PaymentRequestMenuAction(String description) {
    super(description);
  }

  @Override
  public void run() {
    new MemberController().requestPaymentForUnpaidMembers();
  }
}

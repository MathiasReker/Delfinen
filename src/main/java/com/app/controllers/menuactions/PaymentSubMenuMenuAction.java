package com.app.controllers.menuactions;

import com.app.controllers.MenuController;

public class PaymentSubMenuMenuAction extends MenuAction {

  public PaymentSubMenuMenuAction(String description) {
    super(description);
  }

  @Override
  public void run() {
    new MenuController("Payment Menu", "Please choose an option: ", setupMenu()).run();
  }

  private MenuAction[] setupMenu() {
    return new MenuAction[] {
      new RenewalRequestMenuAction("Renew Expiring Members"),
      new PaymentRequestMenuAction("Create Payment Request for unpaid membership"),
      new HandlePaymentMenuAction(("Handle incoming payments")),
      new PredictIncomeMenuAction("Predict Income - input days"),
      new PredictIncomeDefinedMenuAction("Predict Income - 30 days"),
      new DisplayArrearsMenuAction("Display member in arrears"),
      new ExitMenuAction("Back"),
    };
  }
}

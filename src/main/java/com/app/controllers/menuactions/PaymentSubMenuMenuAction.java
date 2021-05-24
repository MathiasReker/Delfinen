package com.app.controllers.menuactions;

import com.app.controllers.MenuController;

public class PaymentSubMenuMenuAction extends MenuActions {

  public PaymentSubMenuMenuAction(String description) {
    super(description);
  }

  @Override
  public void run() {
    new MenuController("Payment Menu", "Please choose an option: ", setupMenu()).run();
  }

  private MenuActions[] setupMenu() {
    return new MenuActions[] {
      new RenewalRequestMenuAction("Request payment for expiring members"),
      new RenewMembershipsMenuAction(("Renew paid memberships")),
      new PredictIncomeMenuAction("Predict Income - input days"),
      new PredictIncomeDefinedMenuAction("Predict Income - 30 days"),
      new ExitMenuAction("Back"),
    };
  }
}

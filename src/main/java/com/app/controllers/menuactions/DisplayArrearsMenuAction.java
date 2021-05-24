package com.app.controllers.menuactions;

import com.app.controllers.PaymentController;

public class DisplayArrearsMenuAction extends MenuAction {
  public DisplayArrearsMenuAction(String description) {
    super(description);
  }

  @Override
  public void run() {
    new PaymentController().displayMembersInArray();
  }
}

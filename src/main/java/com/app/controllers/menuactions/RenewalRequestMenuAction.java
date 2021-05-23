package com.app.controllers.menuactions;

import com.app.controllers.MemberController;


public class RenewalRequestMenuAction extends MenuActions {

  public RenewalRequestMenuAction(String description) {
    super(description);
  }

  public void run() {
    new MemberController().requestRenewalFromExpiringMembers();
  }
}

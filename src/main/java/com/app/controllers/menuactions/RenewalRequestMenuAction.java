package com.app.controllers.menuactions;

import com.app.controllers.MemberController;

public class RenewalRequestMenuAction extends MenuAction {

  public RenewalRequestMenuAction(String description) {
    super(description);
  }

  @Override
  public void run() {
    new MemberController().renewExpiringMembers();
  }
}

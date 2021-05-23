package com.app.controllers.menuactions;

import com.app.controllers.MemberController;

public class CreateMemberSubMenuAction extends MenuActions {
  public CreateMemberSubMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    new MemberController().createMember();
  }
}

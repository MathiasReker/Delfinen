package com.app.controllers.menuactions;

import com.app.controllers.MemberController;

public class CreateMemberMenuAction extends MenuAction {
  public CreateMemberMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    new MemberController().createMember();
  }
}

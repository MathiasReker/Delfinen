package com.app.controllers.menuactions;

import com.app.controllers.MemberController;

public class ViewMemberMenuAction extends MenuActions {

  public ViewMemberMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    new MemberController().viewMembers();
  }
}

package com.app.controllers.menuactions;

import com.app.controllers.MemberController;

public class EditMemberMenuAction extends MenuAction {
  public EditMemberMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    MemberController memberController = new MemberController();
    memberController.viewTableMembers();
    memberController.editMember();
  }
}

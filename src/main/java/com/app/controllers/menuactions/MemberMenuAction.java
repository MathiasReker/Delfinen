package com.app.controllers.menuactions;

import com.app.controllers.MenuController;

public class MemberMenuAction extends MenuActions {
  public MemberMenuAction(String description) {
    super(description);
  }

  public void run() {
    new MenuController("Member management", "Please choose a menu option: ", menu()).run();
  }

  MenuActions[] menu() {
    return new MenuActions[] {
      new CreateNewMemberMenuAction("Create new member"),
      new EditMemberMenuAction("Edit member"),
      new ExitMenuAction("Back")
    };
  }
}

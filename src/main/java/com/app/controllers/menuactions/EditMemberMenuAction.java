package com.app.controllers.menuactions;

import com.app.controllers.MemberController;

public class EditMemberMenuAction extends MenuActions {
  /**
   * Stop the program from running.
   *
   * @param itemName String that describes the menu action.
   */
  public EditMemberMenuAction(String itemName) {
    super(itemName, false);
  }

  /** No business logic should be done as this menu action shut down the program. */
  @Override
  public void run() {
    new MemberController().viewMembers();
  }
}

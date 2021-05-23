package com.app.models;

import com.app.controllers.MemberController;
import com.app.controllers.menuactions.MenuActions;

public class SearchMemberMenuAction extends MenuActions {
  /**
   * Stop the program from running.
   *
   * @param itemName String that describes the menu action.
   */
  public SearchMemberMenuAction(String itemName) {
    super(itemName, false);
  }

  /** No business logic should be done as this menu action shut down the program. */
  @Override
  public void run() {
    new MemberController().viewMemberByName();
  }
}

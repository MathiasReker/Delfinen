package com.app.controllers.menuactions;

import com.app.controllers.MenuController;

public class MemberSubMenuMenuAction extends MenuAction {
  public MemberSubMenuMenuAction(String description) {
    super(description);
  }

  @Override
  public void run() {
    new MenuController("Member management", "Please choose a menu option: ", menu()).run();
  }

  MenuAction[] menu() {
    return new MenuAction[] {
      new CreateMemberMenuAction("Create member"),
      new ViewMemberMenuAction("View all members"),
      new EditMemberMenuAction("Edit member"),
      new AnonymizeMenuAction("Anonymize member"),
      new SearchMemberMenuAction("Search member"),
      new ExitMenuAction("Back")
    };
  }
}

package com.app.controllers.menuactions;

import com.app.controllers.MenuController;
import com.app.models.SearchMemberMenuAction;

public class MemberMenuAction extends MenuActions {
  public MemberMenuAction(String description) {
    super(description);
  }

  public void run() {
    new MenuController("Member management", "Please choose a menu option: ", menu()).run();
  }

  MenuActions[] menu() {
    return new MenuActions[] {
      new CreateMemberSubMenuAction("Create member"),
      new ViewMemberMenuAction("View member"),
      new EditMemberMenuAction("Edit member"),
      new AnonymizeMenuAction("Anonymize member"),
      new SearchMemberMenuAction("Search member"),
      new RenewalRequestMenuAction("Send payment requests for expiring Members"),
      new RenewMembershipsMenuAction(("Renew paid memberships ")),
      new ExitMenuAction("Back")
    };
  }
}

package com.app.controllers.menuactions;

import com.app.controllers.MemberController;

public class SearchMemberMenuAction extends MenuAction {

  public SearchMemberMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    new MemberController().searchMember();
  }
}

package com.app.controllers.menuactions;

import com.app.controllers.MemberController;

import java.util.Scanner;

public class EditMemberMenuAction extends MenuActions {
  public EditMemberMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    Scanner in = new Scanner(System.in);
    new MemberController().viewMembers();
    new MemberController().editMember(in);
  }
}

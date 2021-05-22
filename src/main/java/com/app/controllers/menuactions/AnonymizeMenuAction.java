package com.app.controllers.menuactions;

import com.app.controllers.MemberController;

import java.util.Scanner;

public class AnonymizeMenuAction extends MenuActions {
  /**
   * Stop the program from running.
   *
   * @param itemName String that describes the menu action.
   */
  public AnonymizeMenuAction(String itemName) {
    super(itemName, false);
  }

  /** No business logic should be done as this menu action shut down the program. */
  @Override
  public void run() {
    Scanner in = new Scanner(System.in);
    MemberController member = new MemberController();
    member.viewMembers();
    member.anonymizeMember(in);
  }
}

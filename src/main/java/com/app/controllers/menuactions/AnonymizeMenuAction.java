package com.app.controllers.menuactions;

import com.app.controllers.MemberController;

import java.util.Scanner;

public class AnonymizeMenuAction extends MenuActions {
  public AnonymizeMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    MemberController member = new MemberController();
    member.viewMembers();
    member.anonymizeMember();
  }
}

package com.app.controllers.menuactions;

import com.app.controllers.MemberController;

import java.util.Scanner;

public class CreateMemberSubMenuAction extends MenuActions {
  public CreateMemberSubMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    Scanner in = new Scanner(System.in);
    MemberController memberController = new MemberController();
    memberController.createMember(in);
  }
}

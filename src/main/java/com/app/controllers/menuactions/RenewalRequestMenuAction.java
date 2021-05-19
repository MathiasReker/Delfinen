package com.app.controllers.menuactions;

import com.app.controllers.MemberController;

import java.util.Scanner;

public class RenewalRequestMenuAction extends MenuActions {

  public RenewalRequestMenuAction(String description) {
    super(description);
  }

  public void run() {
    Scanner in = new Scanner(System.in);
    new MemberController().requestRenewalFromExpiringMembers(in);
  }
}

package com.app.controllers;

import com.app.models.GenderModel;
import com.app.models.MemberModel;
import com.app.views.MemberView;

import java.time.LocalDate;
import java.util.Scanner;

public class MemberController {
  private final MemberModel MEMBER;
  private final MemberView MEMBER_VIEW;

  public MemberController() {
    MEMBER = new MemberModel();
    MEMBER_VIEW = new MemberView();
  }

  private String[] genders() {
    String[] result = new String[GenderModel.values().length];

    for (int i = 0; i < result.length; i++) {
      result[i] = GenderModel.values()[i].name();
    }

    return result;
  }

  private boolean yesNo(Scanner in) {
    String input = in.nextLine();
    while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
      MEMBER_VIEW.printInlineWarning("Not a valid menu choice. Please try again: ");

      if (input.equals("Y")) {
        return true;
      } else if (input.equals("N")) {
        return false;
      } else {
        input = in.nextLine();
      }
    }

    return false;
  }

  public void createMember(Scanner in) {

    System.out.println(yesNo(in));
    // Asks for input constantly until it gets y or n

    MEMBER_VIEW.printInline("ID: ");
    String id = in.nextLine();

    MEMBER_VIEW.printInline("Name: ");
    String name = in.nextLine();

    MEMBER_VIEW.displayGenderMenu(genders());
    int gender = in.nextInt();
    in.nextLine();

    MEMBER_VIEW.printInline("Birthday [like 9/10/1992]: ");
    String birthday = in.nextLine();

    MEMBER_VIEW.printInline("Phone: ");
    String phone = in.nextLine();

    MEMBER_VIEW.printInline("Mail: ");
    String mail = in.nextLine();

    MEMBER_VIEW.printInline("Competitive [Y/n]: ");
    boolean competitive = false;

    MEMBER.setID(id);
    MEMBER.setName(name);
    MEMBER.setGender(GenderModel.values()[gender]);
    MEMBER.setBirthdate(LocalDate.parse(birthday));
    MEMBER.setPhoneNumber(phone);
    MEMBER.setMail(mail);
    MEMBER.setCompetitive(competitive);
  }
}

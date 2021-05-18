package com.app.controllers;

import com.app.models.GenderModel;
import com.app.models.MemberModel;
import com.app.views.MemberView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Scanner;

public class MemberController {
  private final MemberModel MEMBER;
  private final MemberView MEMBER_VIEW;

  public MemberController() {
    MEMBER = new MemberModel();
    MEMBER_VIEW = new MemberView();
  }

  public void createMember(Scanner in) {

    MEMBER_VIEW.printInline("ID: ");
    String id = in.nextLine();

    MEMBER_VIEW.printInline("Name: ");
    String name = validateName(in);

    MEMBER_VIEW.displayGenderMenu(gendersToArray());
    int genderIndex = in.nextInt();
    in.nextLine();

    MEMBER_VIEW.printInline("Birthday [dd/MM/yyyy]: ");
    String birthday = validateDate(in);

    MEMBER_VIEW.printInline("Phone: ");
    String phone = validatePhoneNumber(in);

    MEMBER_VIEW.printInline("Mail: ");
    String mail = validateMail(in);

    MEMBER_VIEW.printInline("Competitive [Y/n]: ");
    boolean competitive = promptYesNo(in);

    MEMBER.setID(id);
    MEMBER.setName(name);
    MEMBER.setGender(GenderModel.values()[genderIndex]);
      MEMBER.setBirthdate(LocalDate.parse(birthday));
    MEMBER.setPhoneNumber(phone);
    MEMBER.setMail(mail);
    MEMBER.setCompetitive(competitive);
  }

  private String[] gendersToArray() {
    String[] result = new String[GenderModel.values().length];

    for (int i = 0; i < result.length; i++) {
      result[i] = GenderModel.values()[i].name();
    }

    return result;
  }

  private String validateName(Scanner in) {
    while (!in.hasNext("[a-zA-Z]+")) {
      MEMBER_VIEW.printInlineWarning("Not a valid name. Please try again: ");
      in.nextLine();
    }
    return in.nextLine();
  }

  private String validateMail(Scanner in) {
    while (!in.hasNext("[A-Za-z0-9+_.-]+@(.+)")) {
      MEMBER_VIEW.printInlineWarning("Not a valid mail. Please try again: ");
      in.nextLine();
    }
    return in.nextLine();
  }

  private String validatePhoneNumber(Scanner in) {
    while (!in.hasNext("([+](\\d{1,3})\\s?)?((\\(\\d{3,5}\\)|\\d{3,5})(\\s)?)\\d{3,8}")) {
      MEMBER_VIEW.printInlineWarning("Not a valid phone number. Please try again: ");
      in.nextLine();
    }
    return in.nextLine();
  }

  private String validateDate(Scanner in) {
    while (!isValidDate(in.next())) {
      MEMBER_VIEW.printInlineWarning("Not a valid date. Please try again: ");
      in.nextLine();
    }
    return in.nextLine();
  }

  private boolean isValidDate(String date) {
    if (!date.trim().equals("")) {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
      simpleDateFormat.setLenient(false);
      try {
        simpleDateFormat.parse(date);
        return true;
      } catch (ParseException e) {
        return false;
      }
    }

    return false;
  }

  private boolean promptYesNo(Scanner in) {
    String input = in.nextLine();
    while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
      MEMBER_VIEW.printInlineWarning("Not a valid choice. Please try again: ");

      if (input.equalsIgnoreCase("y")) {
        return true;
      } else if (input.equalsIgnoreCase("N")) {
        return false;
      } else {
        input = in.nextLine();
      }
    }

    return false;
  }
}

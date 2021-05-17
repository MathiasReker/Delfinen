package com.app.controllers;

import com.app.models.GenderModel;
import com.app.models.MemberModel;

import java.time.LocalDate;
import java.util.Scanner;

public class MemberController {
  public void createMember(Scanner in) {
    MemberModel member = new MemberModel();

    System.out.print("ID: ");
    String id = in.nextLine();

    System.out.print("Name: ");
    String name = in.nextLine();

    System.out.print("Gender [1 = MALE, 2 = FEMALE, 3 = OTHER]: ");
    int gender = in.nextInt();
    in.nextLine();

    System.out.print("Birthday [like 9/10/1992]: ");
    String birthday = in.nextLine();

    System.out.print("Phone: ");
    String phone = in.nextLine();

    System.out.print("Mail: ");
    String mail = in.nextLine();

    System.out.print("Competitive: ");
    boolean competitive = in.nextBoolean();

    member.setID(id);
    member.setName(name);
    member.setGender(GenderModel.values()[gender]);
    member.setBirthdate(LocalDate.parse(birthday));
    member.setPhoneNumber(phone);
    member.setMail(mail);
    member.setCompetitive(competitive);
  }
}

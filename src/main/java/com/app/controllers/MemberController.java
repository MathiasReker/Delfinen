package com.app.controllers;

import com.app.models.MemberModel;
import com.app.models.MemberNotFoundException;
import com.app.models.MembershipModel;
import com.app.models.services.PaymentRequestService;
import com.app.models.GenderModel;
import com.app.views.MemberView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

  /**
   * Method for renewing memberships.
   *
   * @param member Member to renew
   * @param durationYears years to add to membership
   */
  public void renewMembership(MemberModel member, int durationYears) { // what
    ArrayList<MembershipModel> memberships = member.getMemberships();
    MembershipModel lastMembership = memberships.get(memberships.size() - 1);
    if (lastMembership.getExpiringDate().compareTo(LocalDate.now()) < 0) {
      MembershipModel newMembership = createNewMembership(LocalDate.now(), durationYears);
      member.addMembership(newMembership);
    } else if (lastMembership.getExpiringDate().compareTo(LocalDate.now()) > 0) {
      MembershipModel newMembership =
          createNewMembership(lastMembership.getExpiringDate().plusDays(1), durationYears);
      member.addMembership(newMembership);
    } else {
      MembershipModel newMembership =
          createNewMembership(LocalDate.now().plusDays(1), durationYears);
      member.addMembership(newMembership);
    }
  }

  private MembershipModel createNewMembership(LocalDate date, int durationYears) {
    MembershipModel result = new MembershipModel();
    result.setStartingDate(date);
    result.setExpiringDate(result.getExpiringDate().plusYears(durationYears));
    result.setActive(true);
    result.setPayed(false);
    return result;
  }

  public void requestRenewalFromExpiringMembers(Scanner in) { // WIP
    try {
      ArrayList<MemberModel> expiringMembers = getExpiringMembers(createMembersForTest(), 30);
      PaymentRequestService paymentRequester =
          new PaymentRequestService("data/payment-requests/out.txt");

      MEMBER_VIEW.print("Expiring members:"); // show Members
      for (MemberModel member : expiringMembers) {
        MEMBER_VIEW.print(member.getID() + "\t" + member.getName() + "\n");
      }
      if (expiringMembers.size() > 0) {
        boolean stop = false;
        while (!stop) { // allow removal of members
          MEMBER_VIEW.print("do you want to remove a member from the list? [Y/n]:");
          if (promptYesNo(in)) {
            MEMBER_VIEW.print("Type member ID to delete:");
            String input = in.nextLine();
            try {
              MemberModel member = getMemberByID(input, expiringMembers);
              expiringMembers.remove(member);
            } catch (MemberNotFoundException e) {
              MEMBER_VIEW.printWarning("Member was not found");
            }
          }
        }
        MEMBER_VIEW.print("Are you sure? [Y/n[");
        if (promptYesNo(in)) {
          paymentRequester.createPaymentRequest(expiringMembers.toArray(new MemberModel[0]));
        }
      }
    } catch (IOException e) {
      System.out.println("cant do that");
    }
  }

  MemberModel getMemberByID(String id, ArrayList<MemberModel> members)
      throws MemberNotFoundException {
    for (MemberModel member : members) {
      if (member.getID() == id) {
        return member;
      }
    }
    throw new MemberNotFoundException();
  }

  /**
   * Returns an Arraylist of expiring members based on the Array given as argument
   *
   * @param days Amount of days to look ahead of current day.
   * @param memberModels Array of members to look through
   * @return ArrayList of expiring members
   */
  private ArrayList<MemberModel> getExpiringMembers(
      MemberModel[] memberModels, int days) { // TODO Move to model?
    ArrayList<MemberModel> result = new ArrayList<>();

    for (MemberModel member : memberModels) {
      ArrayList<MembershipModel> memberships = member.getMemberships();
      LocalDate expiringDate = memberships.get(memberships.size() - 1).getExpiringDate();
      if (expiringDate.minusDays(days).compareTo(LocalDate.now()) <= 0) {
        result.add(member);
      }
    }
    return result;
  }

  // Delete when depricated
  private MemberModel[] createMembersForTest() {
    MemberModel[] members = {
      new MemberModel(), new MemberModel(), new MemberModel(), new MemberModel()
    };

    for (MemberModel member : members) {
      int test = 10;
      member.setID("M" + test);
      member.setName("Name" + test);
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
      member.addMembership(new MembershipModel());
      member.getMemberships().get(0).setExpiringDate(LocalDate.parse(test + "-10-2020", formatter));
      member.getMemberships().get(0).setActive(true);
      member.getMemberships().get(0).setPayed(true);
      test++;
    }

    return members;
  }
}

package com.app.controllers;

import com.app.models.*;
import com.app.models.services.MemberService;
import com.app.models.services.PaymentRequestService;
import com.app.views.MemberView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class MemberController {
  private final MemberView MEMBER_VIEW;
  private final ArrayList<MemberModel> MEMBERS = new ArrayList<>();
  private final String FILE = "data/members.txt";

  public MemberController() {
    MEMBER_VIEW = new MemberView();
  }

  public void createMember(Scanner in) {
    MEMBER_VIEW.printInline("Name: ");
    String name = validateName(in);

    MEMBER_VIEW.printInline("Mail: ");
    String mail = validateMail(in);

    MEMBER_VIEW.displayOptions(gendersToArray());
    int genderIndex = validateOptionRange(in, GenderModel.values().length);
    GenderModel gender = GenderModel.values()[genderIndex - 1];

    MEMBER_VIEW.printInline("Birthday [dd/MM/yyyy]: ");
    String birthday = validateDate(in);

    MEMBER_VIEW.printInline("Phone: ");
    String phone = validatePhoneNumber(in);

    MEMBER_VIEW.printInline("Competitive [Y/n]: ");
    boolean competitive = promptYesNo(in);

    String id = UUID.randomUUID().toString();

    addMember(id, name, mail, gender, birthday, phone, competitive);
    saveMember();
  }

  private void addMember(
      String id,
      String name,
      String mail,
      GenderModel gender,
      String birthday,
      String phone,
      boolean competitive) {
    MemberModel member = new MemberModel();
    member.setID(id);
    member.setName(name);
    member.setMail(mail);
    member.setGender(gender);
    member.setBirthdate(LocalDate.parse(birthday, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    member.setPhoneNumber(phone);
    member.setCompetitive(competitive);

    MEMBERS.add(member);
  }

  private void saveMember() {
    try {
      new MemberService(FILE).storeMembers(MEMBERS);
      MEMBER_VIEW.printSuccess("The member has been saved.");
    } catch (IOException e) {
      MEMBER_VIEW.printWarning(e.getMessage());
    }
  }

  public void loadMembers() {
    try {
      String[] members = new MemberService(FILE).loadMembers();
      for (String m : members) {
        String[] information = m.split(";");
        addMember(
            information[0],
            information[1],
            information[2],
            GenderModel.valueOf(information[3]),
            information[4],
            information[5],
            Boolean.parseBoolean(information[6]));
      }

    } catch (IOException e) {
      MEMBER_VIEW.printWarning(e.getMessage());
    }
  }

  private String[] gendersToArray() {
    String[] result = new String[GenderModel.values().length];
    for (int i = 0; i < result.length; i++) {
      result[i] = GenderModel.values()[i].name();
    }

    return result;
  }

  private String validateName(Scanner in) {
    while (true) {
      String result = in.nextLine();
      if (ValidateModel.isValidName(result)) {
        return result;
      }
      MEMBER_VIEW.printInlineWarning("Not a valid name. Please try again: ");
    }
  }

  private String validateMail(Scanner in) {
    while (true) {
      String result = in.nextLine();
      if (ValidateModel.isValidMail(result)) {
        return result;
      }
      MEMBER_VIEW.printInlineWarning("Not a valid mail. Please try again: ");
    }
  }

  private String validatePhoneNumber(Scanner in) {
    while (true) {
      String result = in.nextLine().trim();
      if (ValidateModel.isValidPhoneNumber(result)) {
        return result;
      }
      MEMBER_VIEW.printInlineWarning("Not a valid phone number. Please try again: ");
    }
  }

  private String validateDate(Scanner in) {
    String result = in.nextLine();
    while (!ValidateModel.isValidDate(result)) {
      MEMBER_VIEW.printInlineWarning("Not a valid date. Please try again: ");
      result = in.nextLine();
    }

    return result;
  }

  private int validateOptionRange(Scanner in, int max) {
    while (true) {
      int result = validateInteger(in);

      if (ValidateModel.isValidRange(result, 1, max)) {
        in.nextLine();
        return result;
      }
      MEMBER_VIEW.printInlineWarning("Not a valid choice. Please try again: ");
    }
  }

  private int validateInteger(Scanner in) {
    while (!in.hasNextInt()) {
      MEMBER_VIEW.printInlineWarning("Not a valid choice. Please try again: ");
      in.next();
    }

    return in.nextInt();
  }

  private boolean promptYesNo(Scanner in) {
    String input = in.nextLine();
    while (true) {
      if (input.equalsIgnoreCase("y")) {
        return true;
      }
      if (input.equalsIgnoreCase("n")) {
        return false;
      }
      MEMBER_VIEW.printInlineWarning("Not a valid choice. Please try again: ");
      input = in.nextLine();
    }
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
    int comparedDate = lastMembership.getExpiringDate().compareTo(LocalDate.now());
    if (comparedDate < 0) {
      MembershipModel newMembership = createNewMembership(LocalDate.now(), durationYears);
      member.addMembership(newMembership);
    } else if (comparedDate > 0) {
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
        MEMBER_VIEW.print(
            member.getID()
                + "\t"
                + member.getName()
                + "\t"
                + member.getLatestMembership().getExpiringDate()
                + "\n");
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
          } else {
            stop = true;
          }
        }
        MEMBER_VIEW.print("Are you sure you want to send payment requests? [Y/n[");
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
      if (member.getID().equals(id)) {
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

    int test = 10;
    for (MemberModel member : members) {
      member.setID("M" + test);
      member.setName("Name" + test);
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
      member.setBirthdate(LocalDate.parse("01-01-2020", formatter));
      member.addMembership(new MembershipModel());
      member.getMemberships().get(0).setExpiringDate(LocalDate.parse(test + "-10-2020", formatter));
      member.getMemberships().get(0).setActive(true);
      member.getMemberships().get(0).setPayed(true);
      test++;
    }

    return members;
  }
}

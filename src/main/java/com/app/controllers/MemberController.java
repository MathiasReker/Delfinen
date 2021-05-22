package com.app.controllers;

import com.app.controllers.utils.Input;
import com.app.models.*;
import com.app.models.services.MemberService;
import com.app.models.services.PaymentRequestService;
import com.app.views.MemberView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MemberController {
  private final MemberView MEMBER_VIEW;
  private final String FILE = "data/members.bin";
  private ArrayList<MemberModel> members;

  public MemberController() {
    MEMBER_VIEW = new MemberView();
    try {
      members = memberToStringArray(loadMembers());
    } catch (CouldNotLoadMemberException e) {
      MEMBER_VIEW.printWarning("Could not load any members");
      members = new ArrayList<>();
    }
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
    boolean competitive = Input.promptYesNo(in);

    String id = generateID();

    addMember(id, name, mail, gender, birthday, phone, competitive);
    saveMembers();
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

    members.add(member);
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

  // TODO: Refactor into shorter methods
  public void requestRenewalFromExpiringMembers(Scanner in) { // WIP
    try {
      ArrayList<MemberModel> expiringMembers =
          getExpiringMembers(members.toArray(new MemberModel[0]), 30);
      PaymentRequestService paymentRequester =
          new PaymentRequestService("data/payment-requests/out.txt");

      MEMBER_VIEW.print("Expiring members:"); // show Members
      for (MemberModel member : expiringMembers) {
        MEMBER_VIEW.print( // TODO
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
          if (Input.promptYesNo(in)) {
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
        if (Input.promptYesNo(in)) {
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

  // TODO: Refactor. Avoid using overload in this case.
  // Har overloaded den her metode da jeg skal bruge array i denne klasse
  MemberModel getMemberByID(String id) throws MemberNotFoundException {
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
  ArrayList<MemberModel> getExpiringMembers(
      MemberModel[] memberModels, int days) { // TODO: Move to model
    ArrayList<MemberModel> result = new ArrayList<>();

    for (MemberModel member : memberModels) {
      ArrayList<MembershipModel> memberships = member.getMemberships();
      if (memberships.size() != 0) {
        LocalDate expiringDate = memberships.get(memberships.size() - 1).getExpiringDate();
        if (expiringDate.minusDays(days).compareTo(LocalDate.now()) <= 0) {
          result.add(member);
        }
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

  public ArrayList<MemberModel> getMEMBERS() {
    return members;
  }

  public void saveMembers() {
    try {
      new MemberService(FILE).saveMembers(members.toArray(new MemberModel[0]));
      MEMBER_VIEW.printSuccess("The member has been saved.");
    } catch (IOException e) {
      MEMBER_VIEW.printWarning(e.getMessage());
    }
  }

  public MemberModel[] loadMembers() throws CouldNotLoadMemberException {
    try {
      return new MemberService(FILE).loadMembers();
    } catch (IOException | ClassNotFoundException e) {
      MEMBER_VIEW.printWarning(e.getMessage());
      return new MemberModel[0];
    }
  }

  private ArrayList<MemberModel> memberToStringArray(MemberModel[] members) { // TODO: REname
    return new ArrayList<>(Arrays.asList(members));
  }

  public void viewMembers() {
    String[] header = new String[] {"ID", "Name", "Mail", "Phone", "Age", "Gender"};
    MEMBER_VIEW.displayMember(header, getColumnWidth());

    for (MemberModel member : members) {
      String[] body =
          new String[] {
            member.getID(),
            member.getName(),
            member.getMail(),
            member.getPhoneNumber(),
            String.valueOf(member.getAge()),
            String.valueOf(member.getGender()),
          };

      MEMBER_VIEW.displayMember(body, getColumnWidth());
    }
  }

  public int[] getColumnWidth() {
    int[] result = new int[7];

    for (MemberModel member : members) {
      String[] arr =
          new String[] {
            member.getID(),
            member.getName(),
            member.getMail(),
            member.getPhoneNumber(),
            String.valueOf(member.getAge()),
            String.valueOf(member.getGender()),
          };

      for (int i = 0; i < arr.length; i++) {
        if (arr[i] == null) {
          arr[i] = "--";
        }

        if (arr[i].length() > result[i]) {
          result[i] = arr[i].length();
        }
      }
    }

    return result;
  }

  public void viewMemberByName(Scanner in) {
    MEMBER_VIEW.printInline("Name: ");
    String name = validateName(in);

    ArrayList<MemberModel> sortedList = getMemberByName(name);
    // MEMBER_VIEW.displaySortedMembers(memberToArray(sortedList));
    // todo: handle if 0 members

    // todo: If there is more than one match, the matches are displayed, and the user must choose by
    // picking a corresponding number
  }

  public ArrayList<MemberModel> getMemberByName(String name) {

    ArrayList<MemberModel> result = new ArrayList<>();

    for (MemberModel m : members) {
      if (m.getName().equals(name)) {
        result.add(m);
      }
    }

    return result;
  }

  public void anonymizeMember() {
    // todo: display members
    // todo: select member

    members.get(0).anonymizeMemberByIndex(0);
    saveMembers();
  }

  private String generateID() { // TODO refactor to valuof
    int id;
    try {
      int temp = Integer.parseInt(members.get(members.size() - 1).getID());
      id = temp + 1;
    } catch (IndexOutOfBoundsException e) {
      id = 1;
    }

    return Integer.toString(id);
  }
}

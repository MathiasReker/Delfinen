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
  private final MemberView VIEW;
  private final String FILE = "data/bin/members.bin";
  private ArrayList<MemberModel> members;

  public MemberController() {
    VIEW = new MemberView();
    try {
      members = membersToStringArray(loadMembers());
    } catch (CouldNotLoadMemberException e) {
      VIEW.printWarning("Could not load any members");
      members = new ArrayList<>();
    }
  }

  public void createMember(Scanner in) {
    VIEW.printInline("Name: ");
    String name = validateName(in);

    VIEW.printInline("Mail: ");
    String mail = validateMail(in);

    VIEW.displayOptions(gendersToArray());
    int genderIndex = validateOptionRange(in, GenderModel.values().length);
    GenderModel gender = GenderModel.values()[genderIndex - 1];

    VIEW.printInline("Birthday [dd/MM/yyyy]: ");
    String birthday = validateDate(in);

    VIEW.printInline("Phone: ");
    String phone = validatePhoneNumber(in);

    VIEW.printInline("Competitive [Y/n]: ");
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
      VIEW.printInlineWarning("Not a valid name. Please try again: ");
    }
  }

  private String validateMail(Scanner in) {
    while (true) {
      String result = in.nextLine();
      if (ValidateModel.isValidMail(result)) {
        return result;
      }
      VIEW.printInlineWarning("Not a valid mail. Please try again: ");
    }
  }

  private String validatePhoneNumber(Scanner in) {
    while (true) {
      String result = in.nextLine().trim();
      if (ValidateModel.isValidPhoneNumber(result)) {
        return result;
      }
      VIEW.printInlineWarning("Not a valid phone number. Please try again: ");
    }
  }

  private String validateDate(Scanner in) {
    String result = in.nextLine();
    while (!ValidateModel.isValidDate(result)) {
      VIEW.printInlineWarning("Not a valid date. Please try again: ");
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
      VIEW.printInlineWarning("Not a valid choice. Please try again: ");
    }
  }

  private int validateInteger(Scanner in) {
    while (!in.hasNextInt()) {
      VIEW.printInlineWarning("Not a valid choice. Please try again: ");
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
  public void renewMembership(MemberModel member, int durationYears) { // TODO: what
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
          new MemberModel().getExpiringMembers(members.toArray(new MemberModel[0]), 30);
      PaymentRequestService paymentRequester =
          new PaymentRequestService("data/payment-requests/out.txt");

      VIEW.print("Expiring members:"); // show Members
      for (MemberModel member : expiringMembers) {
        VIEW.print( // TODO
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
          VIEW.print("Do you want to remove a member from the list? [Y/n]:");
          if (Input.promptYesNo(in)) {
            VIEW.print("Type member ID to delete: ");
            String input = in.nextLine();
            try {
              MemberModel member = getMemberByID(input, expiringMembers);
              expiringMembers.remove(member);
            } catch (MemberNotFoundException e) {
              VIEW.printWarning("Member was not found");
            }
          } else {
            stop = true;
          }
        }
        VIEW.print("Are you sure you want to send the payment requests? [Y/n]");
        if (Input.promptYesNo(in)) {
          paymentRequester.createPaymentRequest(expiringMembers.toArray(new MemberModel[0]));
        }
      }
    } catch (IOException e) {
      VIEW.printWarning(e.getMessage());
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

  MemberModel getMemberByID(String id) throws MemberNotFoundException {
    for (MemberModel member : members) {
      if (member.getID().equals(id)) {
        return member;
      }
    }
    throw new MemberNotFoundException();
  }

  public ArrayList<MemberModel> getMembers() {
    return members;
  }

  public void saveMembers() {
    try {
      new MemberService(FILE).saveMembers(members.toArray(new MemberModel[0]));
      VIEW.printSuccess("The member is successfully saved.");
    } catch (IOException e) {
      VIEW.printWarning(e.getMessage());
    }
  }

  public MemberModel[] loadMembers() throws CouldNotLoadMemberException {
    try {
      return new MemberService(FILE).loadMembers();
    } catch (IOException | ClassNotFoundException e) {
      return new MemberModel[0];
    }
  }

  private ArrayList<MemberModel> membersToStringArray(MemberModel[] members) {
    return new ArrayList<>(Arrays.asList(members));
  }

  public void viewMembers() {
    String[] header = new String[] {"ID", "Name", "Mail", "Phone", "Age", "Gender"};
    VIEW.displayMember(header, getColumnWidth());

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

      VIEW.displayMember(body, getColumnWidth());
    }
  }

  public void viewMembers(ArrayList<MemberModel> members) {
    String[] header = new String[] {"ID", "Name", "Mail", "Phone", "Age", "Gender"};
    VIEW.displayMember(header, getColumnWidth());

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

      VIEW.displayMember(body, getColumnWidth());
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
    VIEW.printInline("Name: ");
    String name = validateName(in);

    ArrayList<MemberModel> sortedList = getMemberByName(name);

    if (0 == sortedList.size()) {
      VIEW.printWarning("No members with the name: " + name);
    } else {
      viewMembers(sortedList);
    }
  }

  public ArrayList<MemberModel> getMemberByName(String name) {
    ArrayList<MemberModel> result = new ArrayList<>();

    for (MemberModel m : members) {
      if (null != m.getName()) {
        if (m.getName().equals(name)) {
          result.add(m);
        }
      }
    }

    return result;
  }

  private String getValidId(Scanner in) {
    while (true) {
      String result = in.nextLine();

      if (result.equals("q")) {
        return null;
      }

      for (MemberModel m : members) {
        if (m.getID().equals(result)) {
          return result;
        }
      }

      VIEW.printInlineWarning("Not a valid ID. Please try again: ");
    }
  }

  public void anonymizeMember(Scanner in) {
    VIEW.printInline("Input ID [press \"q\" to quit]: ");
    String id = getValidId(in);

    if (null != id) {
      try {
        MemberModel member = getMemberByID(id);
        member.setName(null);
        member.setPhoneNumber(null);
        member.setMail(null);
        member.setDeleted(true);
      } catch (MemberNotFoundException e) {
        VIEW.printWarning(e.getMessage());
      }

      saveMembers();
    } else {
      VIEW.printSuccess("Action cancelled.");
    }
  }

  public void editMember(Scanner in) {
    VIEW.printInline("Input ID [press \"q\" to quit]: ");

    String id = getValidId(in);

    if (null != id) {
      try {
        MemberModel member = getMemberByID(id);

        String[] options = new String[] {"Name", "Mail", "Phone number", "Birthday"};
        VIEW.displayOptions(options);

        int index = validateOptionRange(in, options.length) - 1;

        VIEW.printInline(options[index] + ": ");

        if (0 == index) {
          String name = validateName(in);
          member.setName(name);
        } else if (1 == index) {
          String mail = validateMail(in);
          member.setMail(mail);
        } else if (2 == index) {
          String mail = validatePhoneNumber(in);
          member.setPhoneNumber(mail);
        } else if (3 == index) { // TODO: Condition '3 == index' is always 'true'
          String birthday = validateDate(in);
          member.setBirthdate(LocalDate.parse(birthday, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }

        saveMembers();
      } catch (MemberNotFoundException e) {
        e.printStackTrace();
      }

    } else {
      VIEW.printSuccess("Action cancelled.");
    }
  }

  private String generateID() {
    int oldId = 0;
    if (members.size() > 0) {
      oldId = Integer.parseInt(members.get(members.size() - 1).getID());
    }
    int newId = oldId + 1;

    return String.valueOf(newId);
  }
}

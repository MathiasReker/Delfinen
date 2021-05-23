package com.app.controllers;

import com.app.models.MemberModel;
import com.app.models.MembershipModel;
import com.app.models.exceptions.CouldNotLoadMemberException;
import com.app.models.exceptions.MemberNotFoundException;
import com.app.models.services.ConfigService;
import com.app.models.services.MemberService;
import com.app.models.services.PaymentRequestService;
import com.app.models.types.GenderType;
import com.app.views.MemberView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class MemberController {
  private final MemberView VIEW;

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

  public void createMember() {
    VIEW.printInline("Name: ");
    String name = InputController.validateName();

    VIEW.printInline("Mail: ");
    String mail = InputController.validateMail();

    VIEW.displayOptions(gendersToArray());
    int genderIndex = InputController.validateOptionRange(GenderType.values().length);
    GenderType gender = GenderType.values()[genderIndex - 1];

    VIEW.printInline("Birthday [dd/MM/yyyy]: ");
    String birthday = InputController.validateDate();

    VIEW.printInline("Phone: ");
    String phone = InputController.validatePhoneNumber();

    VIEW.printInline("Competitive [Y/n]: ");
    boolean competitive = InputController.promptYesNo();

    String id = generateMemberId();

    addMember(id, name, mail, gender, birthday, phone, competitive);
    saveMembers();
  }

  private void addMember(
      String id,
      String name,
      String mail,
      GenderType gender,
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
    String[] result = new String[GenderType.values().length];
    for (int i = 0; i < result.length; i++) {
      result[i] = GenderType.values()[i].name();
    }

    return result;
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
  public void requestRenewalFromExpiringMembers() { // WIP
    try {
      ArrayList<MemberModel> expiringMembers =
          new MemberModel().getExpiringMembers(members.toArray(new MemberModel[0]), 30);
      PaymentRequestService paymentRequester =
          new PaymentRequestService("data/payment-requests/out.txt");

      VIEW.print("Expiring members:"); // show Members
      for (MemberModel member : expiringMembers) {
        VIEW.print( // TODO
            member.getId()
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
          if (InputController.promptYesNo()) {
            VIEW.print("Type member ID to delete: ");
            String input = InputController.validateMemberId(members);
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
        if (InputController.promptYesNo()) {
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
      if (member.getId().equals(id)) {
        return member;
      }
    }
    throw new MemberNotFoundException();
  }

  MemberModel getMemberByID(String id) throws MemberNotFoundException {
    for (MemberModel member : members) {
      if (member.getId().equals(id)) {
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
      new MemberService(new ConfigService("membersBin").getPath())
          .saveMembers(members.toArray(new MemberModel[0]));
      VIEW.printSuccess("The member is successfully saved.");
    } catch (IOException e) {
      VIEW.printWarning(e.getMessage());
    }
  }

  public MemberModel[] loadMembers() throws CouldNotLoadMemberException {
    try {
      return new MemberService(new ConfigService("membersBin").getPath()).loadMembers();
    } catch (IOException | ClassNotFoundException e) {
      return new MemberModel[0];
    }
  }

  private ArrayList<MemberModel> membersToStringArray(MemberModel[] members) {
    return new ArrayList<>(Arrays.asList(members));
  }

  public void viewMembers() {
    if (members.isEmpty()) {
      VIEW.printWarning("No members.");
    } else {
      String[] header = new String[] {"ID", "Name", "Mail", "Phone", "Age", "Gender"};
      VIEW.displayMember(header, getColumnWidth());

      for (MemberModel member : members) {
        String[] body =
            new String[] {
              member.getId(),
              member.getName(),
              member.getMail(),
              member.getPhoneNumber(),
              String.valueOf(member.getAge()),
              String.valueOf(member.getGender()),
            };

        VIEW.displayMember(body, getColumnWidth());
      }
    }
  }

  public void viewMembers(ArrayList<MemberModel> members) {
    if (members.isEmpty()) {
      VIEW.printWarning("No members.");
    } else {
      String[] header = new String[] {"ID", "Name", "Mail", "Phone", "Age", "Gender"};
      VIEW.displayMember(header, getColumnWidth());

      for (MemberModel member : members) {
        String[] body =
            new String[] {
              member.getId(),
              member.getName(),
              member.getMail(),
              member.getPhoneNumber(),
              String.valueOf(member.getAge()),
              String.valueOf(member.getGender()),
            };

        VIEW.displayMember(body, getColumnWidth());
      }
    }
  }

  public int[] getColumnWidth() {
    int[] result = new int[7];

    for (MemberModel member : members) {
      String[] arr =
          new String[] {
            member.getId(),
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

  public void searchMember() {
    if (!members.isEmpty()) {
      String[] options = new String[] {"ID", "Name", "Mail", "Phone number"};
      VIEW.displayOptions(options);

      int index = InputController.validateOptionRange(options.length) - 1;

      VIEW.printInline(options[index] + ": ");

      if (0 == index) {
        viewMemberById();
      } else if (1 == index) {
        viewMemberByName();
      } else if (2 == index) {
        viewMemberByMail();
      } else if (3 == index) {
        viewMemberByPhone();
      }
    }
  }

  public void viewMemberById() {
    String id = InputController.validateMemberId(members);
    ArrayList<MemberModel> sortedList = getMemberById(id);

    if (0 == sortedList.size()) {
      VIEW.printWarning("No members with the ID: " + id);
    } else {
      viewMembers(sortedList);
    }
  }

  public ArrayList<MemberModel> getMemberById(String id) {
    ArrayList<MemberModel> result = new ArrayList<>();

    for (MemberModel m : members) {
      if (null != m.getId()) {
        if (m.getId().equals(id)) {
          result.add(m);
        }
      }
    }

    return result;
  }

  public void viewMemberByName() {
    String name = InputController.validateName();
    ArrayList<MemberModel> sortedList = getMemberByName(name);

    if (0 == sortedList.size()) {
      VIEW.printWarning("No members with the name: " + name);
    } else {
      viewMembers(sortedList);
    }
  }

  public ArrayList<MemberModel> getMemberByName(String name) { // TODO: move
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

  public void viewMemberByMail() {
    String mail = InputController.validateMail();
    ArrayList<MemberModel> sortedList = getMemberByMail(mail);

    if (0 == sortedList.size()) {
      VIEW.printWarning("No members with the mail: " + mail);
    } else {
      viewMembers(sortedList);
    }
  }

  public ArrayList<MemberModel> getMemberByMail(String mail) { // TODO: move
    ArrayList<MemberModel> result = new ArrayList<>();

    for (MemberModel m : members) {
      if (null != m.getMail()) {
        if (m.getMail().equals(mail)) {
          result.add(m);
        }
      }
    }

    return result;
  }

  public void viewMemberByPhone() {
    String phoneNumber = InputController.validatePhoneNumber();
    ArrayList<MemberModel> sortedList = getMemberByPhoneNumber(phoneNumber);

    if (0 == sortedList.size()) {
      VIEW.printWarning("No members with the phone number: " + phoneNumber);
    } else {
      viewMembers(sortedList);
    }
  }

  public ArrayList<MemberModel> getMemberByPhoneNumber(String phoneNumber) { // TODO: move
    ArrayList<MemberModel> result = new ArrayList<>();

    for (MemberModel m : members) {
      if (null != m.getPhoneNumber()) {
        if (m.getPhoneNumber().equals(phoneNumber)) {
          result.add(m);
        }
      }
    }

    return result;
  }

  public void anonymizeMember() {
    if (!members.isEmpty()) {
      VIEW.printInline("Input ID [press \"q\" to quit]: ");
      String id = InputController.validateMemberId(members);

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
  }

  public void editMember() {
    if (!members.isEmpty()) {
      VIEW.printInline("Input ID [press \"q\" to quit]: ");

      String id = InputController.validateMemberId(members);

      if (null != id) {
        try {
          MemberModel member = getMemberByID(id);

          String[] options = new String[] {"Name", "Mail", "Phone number", "Birthday [dd/MM/yyyy]"};
          VIEW.displayOptions(options);

          int index = InputController.validateOptionRange(options.length) - 1;

          VIEW.printInline(options[index] + ": ");

          if (0 == index) {
            String name = InputController.validateName();
            member.setName(name);
          } else if (1 == index) {
            String mail = InputController.validateMail();
            member.setMail(mail);
          } else if (2 == index) {
            String mail = InputController.validatePhoneNumber();
            member.setPhoneNumber(mail);
          } else if (3 == index) { // TODO: Condition '3 == index' is always 'true'
            String birthday = InputController.validateDate();
            member.setBirthdate(
                LocalDate.parse(birthday, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
          }

          saveMembers();
        } catch (MemberNotFoundException e) {
          e.printStackTrace();
        }
      } else {
        VIEW.printSuccess("Action cancelled.");
      }
    }
  }

  private String generateMemberId() {
    int oldId = 0;
    if (members.size() > 0) {
      oldId = Integer.parseInt(members.get(members.size() - 1).getId());
    }
    int result = oldId + 1;

    return String.valueOf(result);
  }
}

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
  private final MembershipController MEMBERSHIP_CONTROLLER = new MembershipController();
  private ArrayList<MemberModel> members;

  public MemberController() {
    VIEW = new MemberView();
    try {
      members = membersToStringArray(loadMembers());
    } catch (CouldNotLoadMemberException e) {
      VIEW.printWarning("Could not load any members.");
      members = new ArrayList<>();
    }
  }

  public void createMember() {
    VIEW.printInline("Name: ");
    String name = InputController.validateName();

    VIEW.printInline("Mail: ");
    String mail = InputController.validateMail();

    VIEW.displayOptions(gendersToArray());
    int genderIndex = InputController.validateOptionRange(GenderType.values().length) - 1;
    GenderType gender = GenderType.values()[genderIndex];

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
    MEMBERSHIP_CONTROLLER.addMembership(member);

    members.add(member);
  }

  private String[] gendersToArray() {
    String[] result = new String[GenderType.values().length];
    for (int i = 0; i < result.length; i++) {
      result[i] = GenderType.values()[i].name();
    }

    return result;
  }

  public void renewExpiringMembers() {
    ArrayList<MemberModel> expiringMembers =
        getExpiringMembers(members.toArray(new MemberModel[0]), 30);

    if (expiringMembers.size() > 0) {
      boolean stop = false;
      while (!stop) { // allow removal of members
        VIEW.print("Expiring members:"); // show Members
        viewMembers(expiringMembers);
        VIEW.print("Do you want to remove a member from the list? [Y/n]:");
        if (InputController.promptYesNo()) {
          expiringMembers = removeMemberFromList(expiringMembers);
        } else {
          stop = true;
        }
      }
      for (MemberModel member : expiringMembers) {
        MEMBERSHIP_CONTROLLER.renewMembership(member, 1);
      }
    }
  }

  public void requestPaymentForUnpaidMembers() {
    try {
      ArrayList<MemberModel> unpaidMembers = getUnpaidMembers(members);
      PaymentRequestService paymentRequester =
          new PaymentRequestService("data/payment-requests/out.txt");
      boolean stop = false;
      while (!stop) { // allow removal of members
        VIEW.print("Unpaid members:");
        viewMembers(unpaidMembers);
        VIEW.print("Do you want to remove a member from the list? [Y/n]:");
        if (InputController.promptYesNo()) {
          unpaidMembers = removeMemberFromList(unpaidMembers);
        } else {
          stop = true;
        }
        if (unpaidMembers.size() > 0) {
          VIEW.print("Are you sure you want to send the payment requests? [Y/n]");
          if (InputController.promptYesNo()) {
            paymentRequester.createPaymentRequest(unpaidMembers.toArray(new MemberModel[0]));
          }
        }
      }

    } catch (IOException e) {
      VIEW.printWarning(e.getMessage());
    }
  }

  private ArrayList<MemberModel> removeMemberFromList(ArrayList<MemberModel> members) {
    ArrayList<MemberModel> result = new ArrayList<>(members);
    VIEW.print("Type member ID to remove: ");
    String input = InputController.validateMemberId(members);
    try {
      MemberModel member = getMemberByID(input, members);
      result.remove(member);
    } catch (MemberNotFoundException e) {
      VIEW.printWarning("Member was not found");
    }

    return result;
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
        viewMemberByPhoneNumber();
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

  public void viewMemberByMail() {
    String mail = InputController.validateMail();
    ArrayList<MemberModel> sortedList = getMemberByMail(mail);

    if (0 == sortedList.size()) {
      VIEW.printWarning("No members with the mail: " + mail);
    } else {
      viewMembers(sortedList);
    }
  }

  public ArrayList<MemberModel> getMemberByMail(String mail) {
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

  public void viewMemberByPhoneNumber() {
    String phoneNumber = InputController.validatePhoneNumber();
    ArrayList<MemberModel> sortedList = getMemberByPhoneNumber(phoneNumber);

    if (0 == sortedList.size()) {
      VIEW.printWarning("No members with the phone number: " + phoneNumber);
    } else {
      viewMembers(sortedList);
    }
  }

  public ArrayList<MemberModel> getMemberByPhoneNumber(String phoneNumber) {
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
          } else if (3 == index) {
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

  /**
   * Returns an Arraylist of expiring members based on the Array given as argument
   *
   * @param days Amount of days to look ahead of current day.
   * @param memberModels Array of members to look through
   * @return ArrayList of expiring members
   */
  public ArrayList<MemberModel> getExpiringMembers(MemberModel[] memberModels, int days) {
    ArrayList<MemberModel> result = new ArrayList<>();

    for (MemberModel member : memberModels) {
      MembershipModel latestMembership = member.getLatestMembership();
      LocalDate expiringDate = latestMembership.getExpiringDate();
      if (expiringDate != null) {
        if (expiringDate.minusDays(days).compareTo(LocalDate.now()) <= 0) {
          result.add(member);
        }
      }
    }

    return result;
  }

  public ArrayList<MemberModel> getUnpaidMembers(ArrayList<MemberModel> memberModels) {
    ArrayList<MemberModel> result = new ArrayList<>();

    for (MemberModel member : memberModels) {
      MembershipModel latestMembership = member.getLatestMembership();
      if (!latestMembership.isPayed() && latestMembership.isActive()) {
        result.add(member);
      }
    }

    return result;
  }
}

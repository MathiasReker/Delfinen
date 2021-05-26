package com.app.controllers;

import com.app.models.DisciplineModel;
import com.app.models.MemberModel;
import com.app.models.exceptions.CouldNotLoadMemberException;
import com.app.models.exceptions.MemberNotFoundException;
import com.app.models.services.ConfigService;
import com.app.models.services.MemberService;
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
  private final DisciplinesController DISC_CONTROLLER = new DisciplinesController();
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

    VIEW.printInline("Active member [Y/n]: ");
    boolean active = InputController.promptYesNo();

    String id = generateMemberId();

    addMember(id, name, mail, gender, birthday, phone, competitive, active);
    saveMembers();
  }

  private void addMember(
      String id,
      String name,
      String mail,
      GenderType gender,
      String birthday,
      String phone,
      boolean competitive,
      boolean active) {
    MemberModel member = new MemberModel();
    member.setID(id);
    member.setName(name);
    member.setMail(mail);
    member.setGender(gender);
    member.setBirthdate(LocalDate.parse(birthday, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    member.setPhoneNumber(phone);
    member.setCompetitive(competitive);

    if (active) {
      MEMBERSHIP_CONTROLLER.addActiveMembership(member);
    } else {
      MEMBERSHIP_CONTROLLER.addPassiveMembership(member);
    }
    members.add(member);
  }

  private String[] gendersToArray() {
    String[] result = new String[GenderType.values().length];
    for (int i = 0; i < result.length; i++) {
      result[i] = GenderType.values()[i].name();
    }

    return result;
  }

  public void renewExpiringMembers(int days) {
    ArrayList<MemberModel> expiringMembers =
        getExpiringMembers(members.toArray(new MemberModel[0]), days);

    if (expiringMembers.size() > 0) {
      expiringMembers = removeMemberFromList(expiringMembers);
    }
    for (MemberModel member : expiringMembers) {
      MEMBERSHIP_CONTROLLER.renewMembership(member, 1);
    }
  }

  public ArrayList<MemberModel> removeMemberFromList(ArrayList<MemberModel> members) {
    ArrayList<MemberModel> result = new ArrayList<>(members);
    boolean stop = false;
    while (!stop) { // Allow removal of members
      VIEW.print("Members:");
      viewTableMembers(members);
      VIEW.printInline("Do you want to remove a member from the list? [Y/n]: ");
      if (InputController.promptYesNo()) {
        VIEW.print("Type member ID to remove: ");
        String input = InputController.validateMemberId(members);
        try {
          MemberModel member = getMemberByID(input, members);
          result.remove(member);
        } catch (MemberNotFoundException e) {
          VIEW.printWarning("Member was not found.");
        }
      } else {
        stop = true;
      }
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
          .save(members.toArray(new MemberModel[0]));
      VIEW.printSuccess("The member is successfully saved.");
    } catch (IOException e) {
      VIEW.printWarning(e.getMessage());
    }
  }

  public MemberModel[] loadMembers() throws CouldNotLoadMemberException {
    try {
      return new MemberService(new ConfigService("membersBin").getPath()).load();
    } catch (IOException | ClassNotFoundException e) {
      return new MemberModel[0];
    }
  }

  private ArrayList<MemberModel> membersToStringArray(MemberModel[] members) {
    return new ArrayList<>(Arrays.asList(members));
  }

  private String[] getMemberLine(MemberModel member) {
    return new String[] {
      member.getId(),
      member.getName(),
      member.getMail(),
      member.getPhoneNumber(),
      String.valueOf(member.getAge()),
      String.valueOf(member.getGender()),
    };
  }

  private String[] getMemberHeader() {
    return new String[] {"ID", "Name", "Mail", "Phone", "Age", "Gender"};
  }

  public void viewTableMembers(MemberModel member) {
    if (members.isEmpty()) {
      VIEW.printWarning("No members.");
    } else {
      String[] header = getMemberHeader();
      VIEW.displayMember(header, getColumnWidth());

      String[] body = getMemberLine(member);
      VIEW.displayMember(body, getColumnWidth());
    }
  }

  public void viewTableMembers(ArrayList<MemberModel> members) {
    if (members.isEmpty()) {
      VIEW.printWarning("No members.");
    } else {
      String[] header = getMemberHeader();
      VIEW.displayMember(header, getColumnWidth());

      for (MemberModel member : members) {
        String[] body = getMemberLine(member);
        VIEW.displayMember(body, getColumnWidth());
      }
    }
  }

  public void viewTableMembers() {
    if (members.isEmpty()) {
      VIEW.printWarning("No members.");
    } else {
      String[] header = getMemberHeader();
      VIEW.displayMember(header, getColumnWidth());

      for (MemberModel member : members) {
        String[] body = getMemberLine(member);
        VIEW.displayMember(body, getColumnWidth());
      }
    }
  }

  public int[] getColumnWidth() {
    int[] result = new int[getMemberHeader().length];

    for (MemberModel member : members) {
      String[] body = getMemberLine(member);

      for (int i = 0; i < body.length; i++) {
        if (body[i] == null) {
          body[i] = "--";
        }

        if (body[i].length() > result[i]) {
          result[i] = body[i].length();
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
        viewMembersByName();
      } else if (2 == index) {
        viewMembersByMail();
      } else if (3 == index) {
        viewMemberByPhoneNumber();
      }
    }
  }

  public void viewMemberById() {
    String id = InputController.validateMemberId(members);

    if (null != getMemberById(id)) {
      viewTableMembers(getMemberById(id));
    } else {
      VIEW.printWarning("No members with the ID: " + id);
    }
  }

  public MemberModel getMemberById(String id) {
    for (MemberModel result : members) {
      if (null != result.getId()) {
        if (result.getId().equals(id)) {
          return result;
        }
      }
    }

    return null;
  }

  public void viewMembersByName() {
    String name = InputController.validateName();
    ArrayList<MemberModel> sortedList = getMembersByName(name);

    if (0 == sortedList.size()) {
      VIEW.printWarning("No members with the name: " + name);
    } else {
      viewTableMembers(sortedList);
    }
  }

  public ArrayList<MemberModel> getMembersByName(String name) {
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

  public void viewMembersByMail() {
    String mail = InputController.validateMail();
    ArrayList<MemberModel> sortedList = getMembersByMail(mail);

    if (0 == sortedList.size()) {
      VIEW.printWarning("No members with the mail: " + mail);
    } else {
      viewTableMembers(sortedList);
    }
  }

  public ArrayList<MemberModel> getMembersByMail(String mail) {
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
      viewTableMembers(sortedList);
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

          String[] options =
              new String[] {
                "Name", "Mail", "Phone number", "Birthday [dd/MM/yyyy]", "Add Discipline"
              };
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
          } else if (4 == index) {
            addDisciplineToMember(member);
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
      if (MEMBERSHIP_CONTROLLER.membershipExpiresInDays(member, days)) {
        result.add(member);
      }
    }

    return result;
  }

  public ArrayList<MemberModel> getUnpaidMembers(ArrayList<MemberModel> memberModels) {
    ArrayList<MemberModel> result = new ArrayList<>();

    for (MemberModel member : memberModels) {
      if (!MEMBERSHIP_CONTROLLER.membershipUnpaid(member)) {
        result.add(member);
      }
    }

    return result;
  }

  private void addDiscipline(MemberModel member, DisciplineModel discipline) {
    if (member.getDisciplines().isEmpty()) {
      member.addDiscipline(discipline);
    } else if (!DISC_CONTROLLER.lookupDiscipline(member.getDisciplines(), discipline)) {
      member.addDiscipline(discipline);
    }
  }

  public void addDisciplineToMember(MemberModel member) {
    VIEW.printInline("Which discipline do you want to add");
    DisciplineModel discipline =
        DISC_CONTROLLER.getDisciplineModelStyleAndDistance(member.getGender());
    addDiscipline(member, discipline);
  }
}

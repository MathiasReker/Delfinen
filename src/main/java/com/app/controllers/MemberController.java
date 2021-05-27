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
  private final DisciplineController DISC_CONTROLLER = new DisciplineController();
  private ArrayList<MemberModel> members;

  /**
   * Member controller.
   *
   * @auther Mathias
   */
  public MemberController() {
    VIEW = new MemberView();
    try {
      members = membersToStringArray(loadMembers());
    } catch (CouldNotLoadMemberException e) {
      VIEW.printWarning("Could not load any members.");
      members = new ArrayList<>();
    }
  }

  /**
   * Create member. The user will input values that will be saved.
   *
   * @auther Mathias
   */
  public void createMember() {
    VIEW.printInline("Name: ");
    String name = InputController.validateName();

    VIEW.printInline("Mail: ");
    String mail = InputController.validateMail();

    VIEW.displayOptions(gendersToArray());
    int genderIndex = InputController.validateOptionRange(GenderType.values().length) - 1;
    GenderType gender = GenderType.values()[genderIndex];

    VIEW.printInline("Birthday [dd/MM/yyyy]: ");
    String birthday = InputController.validateBirthDate();

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

  /**
   * Add member.
   *
   * @param id
   * @param name
   * @param mail
   * @param gender
   * @param birthday
   * @param phone
   * @param competitive
   * @param active
   * @auther Mathias
   */
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

  /**
   * Returns an array of genders.
   *
   * @return array
   * @auther Mathias
   */
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

  ArrayList<MemberModel> removeMemberFromList(ArrayList<MemberModel> members) {
    ArrayList<MemberModel> result = new ArrayList<>(members);
    boolean stop = false;
    while (!stop) { // Allow removal of members
      VIEW.print("Members:"); // TODO: formatting
      viewTableMembers(members, false);
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

  /**
   * Get member by ID.
   *
   * @param id String
   * @param members ArrayList<MemberModel>
   * @return MemberModel
   * @auther Mathias
   */
  MemberModel getMemberByID(String id, ArrayList<MemberModel> members)
      throws MemberNotFoundException {
    for (MemberModel result : members) {
      if (result.getId().equals(id)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Get member by ID.
   *
   * @param id String
   * @return MemberModel
   * @auther Mathias
   */
  MemberModel getMemberByID(String id) throws MemberNotFoundException {
    for (MemberModel result : members) {
      if (result.getId().equals(id)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns an arraylist of members.
   *
   * @return members
   * @auther Mathias
   */
  public ArrayList<MemberModel> getMembers() {
    return members;
  }

  /**
   * Save members.
   *
   * @auther Mathias
   */
  public void saveMembers() {
    try {
      new MemberService(new ConfigService("membersBin").getPath())
          .save(members.toArray(new MemberModel[0]));
      VIEW.printSuccess("The member is successfully saved.");
    } catch (IOException e) {
      VIEW.printWarning(e.getMessage());
    }
  }

  /**
   * Load members.
   *
   * @return
   * @throws CouldNotLoadMemberException
   * @auther Andreas, Mathias
   */
  public MemberModel[] loadMembers() throws CouldNotLoadMemberException {
    try {
      return new MemberService(new ConfigService("membersBin").getPath()).load();
    } catch (IOException | ClassNotFoundException e) {
      return new MemberModel[0];
    }
  }

  /**
   * Converts members to string array.
   *
   * @param members MemberModel[]
   * @return ArrayList<MemberModel>
   * @auther Mathias
   */
  private ArrayList<MemberModel> membersToStringArray(MemberModel[] members) {
    return new ArrayList<>(Arrays.asList(members));
  }

  /**
   * Get member headers used to generate a table.
   *
   * @param expanded boolean. Decides weather to show extended information.
   * @return String[]
   * @auther Mathias
   */
  private String[] getMemberHeader(boolean expanded) {
    if (expanded) {
      return new String[] {
        "ID", "Name", "Mail", "Phone", "Age", "Gender", "Seniority", "Favourite Disciplines"
      };
    }
    return new String[] {"ID", "Name", "Mail", "Phone", "Age", "Gender", "Seniority"};
  }

  /**
   * View members in a table.
   *
   * @param members ArrayList<MemberModel>
   * @auther Mathias
   */
  private void viewTableMembers(ArrayList<MemberModel> members, boolean expanded) {
    if (members.isEmpty()) {
      VIEW.printWarning("No members.");
    } else {
      VIEW.printTable(getMemberHeader(expanded), getMemberContent(members, expanded));
    }
  }

  /**
   * View members in a table.
   *
   * @param member MemberModel
   * @param expanded boolean. Decides weather to show extended information.
   * @auther Mathias
   */
  public void viewTableMembers(MemberModel member, boolean expanded) {
    VIEW.printTable(getMemberHeader(expanded), getMemberContent(member, expanded));
  }

  /**
   * View members in a table.
   *
   * @auther Mathias
   */
  public void viewTableMembers() {
    if (members.isEmpty()) {
      VIEW.printWarning("No members.");
    } else {
      VIEW.printTable(getMemberHeader(false), getMemberContent(members, false));
    }
  }

  /**
   * Returns member content to be used in table view.
   *
   * @param members MemberModel
   * @param expanded boolean. Decides weather to show extended information.
   * @return ArrayList<ArrayList<String>>
   * @auther Mathias
   */
  private ArrayList<ArrayList<String>> getMemberContent(
      ArrayList<MemberModel> members, boolean expanded) {
    ArrayList<ArrayList<String>> result = new ArrayList<>();

    for (MemberModel member : members) {
      result.add(getRows(member, expanded));
    }

    return result;
  }

  /**
   * Returns rows to be used in table view.
   *
   * @param member MemberModel
   * @param expanded boolean. Decides weather to show extended information.
   * @return ArrayList<String>
   * @auther Mathias
   */
  private ArrayList<String> getRows(MemberModel member, boolean expanded) {
    ArrayList<String> result = new ArrayList<>();

    result.add(member.getId());
    result.add(member.getName());
    result.add(member.getMail());
    result.add(member.getPhoneNumber());
    result.add(String.valueOf(member.getAge()));
    result.add(String.valueOf(member.getGender()));
    result.add(String.valueOf(member.getSeniority()));

    if (expanded) {
      result.add(
          String.join(", ", DISC_CONTROLLER.getDisciplineDescriptions(member.getDisciplines())));
    }

    return result;
  }

  /**
   * Returns rows to be used in table view.
   *
   * @param member MemberModel
   * @param expanded boolean. Decides weather to show extended information.
   * @return ArrayList<String>
   * @auther Mathias
   */
  private ArrayList<ArrayList<String>> getMemberContent(MemberModel member, boolean expanded) {
    ArrayList<ArrayList<String>> result = new ArrayList<>();

    result.add(getRows(member, expanded));

    return result;
  }

  /**
   * Search member.
   *
   * @auther Mathias
   */
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

  /**
   * View member by ID.
   *
   * @auther Mathias
   */
  public void viewMemberById() {
    String id = InputController.validateMemberId(members);

    if (null != getMemberById(id)) {
      viewTableMembers(getMemberById(id), true);
    } else {
      VIEW.printWarning("No members with the ID: " + id);
    }
  }

  /**
   * Returns member by ID.
   *
   * @param id String
   * @return MemberModel|null
   */
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

  /**
   * View members by name.
   *
   * @auther
   */
  public void viewMembersByName() {
    String name = InputController.validateName();
    ArrayList<MemberModel> sortedList = getMembersByName(name);

    if (0 == sortedList.size()) {
      VIEW.printWarning("No members with the name: " + name);
    } else {
      viewTableMembers(sortedList, true);
    }
  }

  /**
   * Returns members by name.
   *
   * @param name String
   * @return ArrayList<MemberModel>
   * @auther Mathias
   */
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

  /**
   * View members by mail.
   *
   * @auther Mathias
   */
  public void viewMembersByMail() {
    String mail = InputController.validateMail();
    ArrayList<MemberModel> sortedList = getMembersByMail(mail);

    if (0 == sortedList.size()) {
      VIEW.printWarning("No members with the mail: " + mail);
    } else {
      viewTableMembers(sortedList, true);
    }
  }

  /**
   * Returns members by mail.
   *
   * @param mail String
   * @return ArrayList<MemberModel>
   * @auther Mathias
   */
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

  /**
   * View members by Phone number.
   *
   * @auther Mathias
   */
  public void viewMemberByPhoneNumber() {
    String phoneNumber = InputController.validatePhoneNumber();
    ArrayList<MemberModel> sortedList = getMembersByPhoneNumber(phoneNumber);

    if (0 == sortedList.size()) {
      VIEW.printWarning("No members with the phone number: " + phoneNumber);
    } else {
      viewTableMembers(sortedList, true);
    }
  }

  /**
   * Returns members by phone number.
   *
   * @param phoneNumber
   * @return ArrayList<MemberModel>
   * @auther Mathias
   */
  public ArrayList<MemberModel> getMembersByPhoneNumber(String phoneNumber) {
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

  /**
   * Anonymize member.
   *
   * @auther Mathias
   */
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

  /**
   * Edit member.
   *
   * @auther Mathias
   */
  public void editMember() {
    if (!members.isEmpty()) {
      viewTableMembers();

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
            String birthday = InputController.validateBirthDate();
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

  /**
   * Returns member ID.
   *
   * @return String
   * @auther Mohammad, Mathias
   */
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
    VIEW.printInline("Discipline to add: ");
    DisciplineModel discipline =
        DISC_CONTROLLER.getDisciplineModelStyleAndDistance(member.getGender());
    addDiscipline(member, discipline);
  }
}

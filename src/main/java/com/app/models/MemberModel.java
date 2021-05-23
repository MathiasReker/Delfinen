package com.app.models;

import com.app.models.types.GenderType;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;

public class MemberModel implements Serializable {
  private final LocalDate CREATE_DATE;
  private String ID;
  private String name;
  private LocalDate birthdate;
  private GenderType gender;
  private String phoneNumber;
  private String mail;
  private boolean competitive;
  private ArrayList<DisciplineModel> disciplines = new ArrayList<>();
  private ArrayList<MembershipModel> memberships = new ArrayList<>();
  private boolean deleted;

  public MemberModel() {
    CREATE_DATE = LocalDateTime.now().toLocalDate();
  }

  /**
   * Package Private constructor for instantiating objects in the model.
   *
   * <p>Can be used when reading stored data.
   *
   * @param ID
   * @param name
   * @param birthdate
   * @param gender
   * @param phoneNumber
   * @param mail
   * @param competitive
   * @param disciplines
   * @param creationDate
   * @param memberships
   */
  MemberModel(
      String ID,
      String name,
      LocalDate birthdate,
      GenderType gender,
      String phoneNumber,
      String mail,
      boolean competitive,
      ArrayList<DisciplineModel> disciplines,
      LocalDate creationDate,
      ArrayList<MembershipModel> memberships) {
    setID(ID);
    setName(name);
    this.birthdate = birthdate;
    setGender(gender);
    setPhoneNumber(phoneNumber);
    setMail(mail);
    setCompetitive(competitive);
    this.CREATE_DATE = creationDate;
    setDisciplines(disciplines);
    setMemberships(memberships);
  }

  public void addMembership(MembershipModel membership) {
    memberships.add(membership);
  }

  public void addDiscipline(DisciplineModel discipline) {
    disciplines.add(discipline);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isDeleted() { // TODO: Method 'isDeleted()' is never used
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  public GenderType getGender() {
    return gender;
  }

  public void setGender(GenderType gender) {
    this.gender = gender;
  }

  public LocalDate getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
  }

  public String getID() {
    return ID;
  }

  public void setID(String ID) {
    this.ID = ID;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public LocalDate getCreationDate() {
    return CREATE_DATE;
  }

  public boolean isCompetitive() {
    return competitive;
  }

  public void setCompetitive(boolean competitive) {
    this.competitive = competitive;
  }

  public ArrayList<DisciplineModel> getDisciplines() {
    return disciplines;
  }

  /**
   * Package Private method for setting disciplines when reading members from file.
   *
   * @param disciplines
   */
  void setDisciplines(ArrayList<DisciplineModel> disciplines) {
    this.disciplines = disciplines;
  }

  public ArrayList<MembershipModel> getMemberships() {
    return memberships;
  }

  /**
   * Package private methods for setting Memberships when reading members from file.
   *
   * @param memberships
   */
  void setMemberships(ArrayList<MembershipModel> memberships) {
    this.memberships = memberships;
  }

  public MembershipModel getLatestMembership() {
    ArrayList<MembershipModel> memberships = getMemberships();
    return memberships.get(memberships.size() - 1);
  }

  public int getAge() {
    LocalDate currentDate = LocalDate.now();
    if (birthdate == null) {
      return 0;
    }

    int ageInYears = Period.between(birthdate, currentDate).getYears();

    return Math.max(ageInYears, 0);
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
}

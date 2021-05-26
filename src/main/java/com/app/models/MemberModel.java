package com.app.models;

import com.app.models.types.AgeGroupType;
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
   * Can be used when reading stored data.
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

  public String getId() {
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

  public AgeGroupType getAgeGroup() {
    if (getAge() <= AgeGroupType.JUNIOR.getAge()) {
      return AgeGroupType.JUNIOR;
    } else {
      return AgeGroupType.SENIOR;
    }
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
    if (memberships.size() == 0) {
      return null;
    }

    return memberships.get(memberships.size() - 1);
  }

  public int getAge() {
    LocalDate currentDate = LocalDate.now();

    return Period.between(birthdate, currentDate).getYears();
  }

  public int getSeniority(){
    LocalDate currentDate = LocalDate.now();

    return Period.between(getCreationDate(), currentDate).getYears();
  }
}

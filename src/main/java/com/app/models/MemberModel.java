package com.app.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MemberModel {
  private final LocalDate creationDate;
  private String ID;
  private String name;
  private LocalDate birthdate;
  private GenderModel gender;
  private String phoneNumber;
  private String mail;
  private boolean competitive;
  private ArrayList<DisciplineModel> disciplines = new ArrayList<>();
  private ArrayList<MembershipModel> memberships = new ArrayList<>();

  public MemberModel() {
    creationDate = LocalDateTime.now().toLocalDate();
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
      GenderModel gender,
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
    this.creationDate = creationDate;
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

  public GenderModel getGender() {
    return gender;
  }

  public void setGender(GenderModel gender) {
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

  public String getMail() { return  mail;}

  public void setMail(String mail) {
    this.mail = mail;
  }

  public LocalDate getCreationDate() {
    return creationDate;
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
}

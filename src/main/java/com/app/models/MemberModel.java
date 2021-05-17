package com.app.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MemberModel {
  private String ID;
  private String name;
  private LocalDate birthdate;
  private GenderModel gender;
  private String phoneNumber;
  private boolean competitive;
  private ArrayList<DisciplineModel> disciplines = new ArrayList<>();
  private final LocalDate creationDate;
  private ArrayList<MembershipModel> memberships = new ArrayList<>();

  public MemberModel() {
    creationDate = LocalDateTime.now().toLocalDate();
  }

  /**
   * Package Private constructor for instantiating objects in the model.
   *
   * Can be used when reading stored data.
   *
   * @param ID
   * @param name
   * @param birthdate
   * @param gender
   * @param phoneNumber
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
      boolean competitive,
      ArrayList<DisciplineModel> disciplines,
      LocalDate creationDate,
      ArrayList<MembershipModel> memberships) {
    setID(ID);
    setName(name);
    this.birthdate = birthdate;
    setGender(gender);
    setPhoneNumber(phoneNumber);
    setCompetitive(competitive);
    this.creationDate = creationDate;
    setDisciplines(disciplines);
    setMemberships(memberships);
  }

  public void setID(String ID) {
    this.ID = ID;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setBirthdate(String birthdate) {
    String dateFormat = "dd-MM-yyyy"; // TODO make global?
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
    this.birthdate = LocalDate.parse(birthdate, formatter);
  }

  public void setGender(GenderModel gender) {
    this.gender = gender;
  }

  public void setCompetitive(boolean competitive) {
    this.competitive = competitive;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void addMembership(MembershipModel membership) {
    memberships.add(membership);
  }

  public void addDiscipline(DisciplineModel discipline) {
    disciplines.add(discipline);
  }

  /**
   * Package Private method for setting disciplines when reading members from file.
   *
   * @param disciplines
   */
  void setDisciplines(ArrayList<DisciplineModel> disciplines) {
    this.disciplines = disciplines;
  }

  /**
   * Package private methods for setting Memberships when reading members from file.
   *
   * @param memberships
   */
  void setMemberships(ArrayList<MembershipModel> memberships) {
    this.memberships = memberships;
  }

  public String getName() {
    return name;
  }

  public GenderModel getGender() {
    return gender;
  }

  public LocalDate getBirthdate() {
    return birthdate;
  }

  public String getID() {
    return ID;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public boolean isCompetitive() {
    return competitive;
  }

  public ArrayList<DisciplineModel> getDisciplines() {
    return disciplines;
  }

  public ArrayList<MembershipModel> getMemberships() {
    return memberships;
  }
}

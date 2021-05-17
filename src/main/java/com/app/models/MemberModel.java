package com.app.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MemberModel {
  private String ID;
  private String name;
  private LocalDate age;
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
   * @param age
   * @param gender
   * @param phoneNumber
   * @param competitive
   * @param disciplines
   * @param creationDate
   * @param memberships
   */


  MemberModel(String ID,
              String name,
              LocalDate age,
              GenderModel gender,
              String phoneNumber,
              boolean competitive,
              ArrayList<DisciplineModel> disciplines,
              LocalDate creationDate,
              ArrayList<MembershipModel> memberships){
    setID(ID);
    setName(name);
    setAge(age);
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

  public void setAge(LocalDate age) {
    this.age = age;
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

  public void addMembership(MembershipModel membership){
    memberships.add(membership);
  }

  public void addDiscipline(DisciplineModel discipline){
    disciplines.add(discipline);
  }

  void setDisciplines(ArrayList<DisciplineModel> disciplines){
    this.disciplines = disciplines;
  }

  void setMemberships(ArrayList<MembershipModel> memberships){
    this.memberships = memberships;
  }
}

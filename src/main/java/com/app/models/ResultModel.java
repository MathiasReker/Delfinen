package com.app.models;

import java.time.LocalTime;

public class ResultModel {

  private MemberModel member;
  private LocalTime resultTime;
  private DisciplineModel discipline;

  public ResultModel(MemberModel member, LocalTime resultTime, DisciplineModel discipline) {
    this.member = member;
    this.resultTime = resultTime;
    this.discipline = discipline;
  }

  // getter

  public MemberModel getMember() {
    return member;
  }

  public LocalTime getResultTime() {
    return resultTime;
  }

  public DisciplineModel getDiscipline() {
    return discipline;
  }


  public void setMember(MemberModel member) {
    this.member = member;
  }

  public void setResultTime(LocalTime resultTime) {
    this.resultTime = resultTime;
  }

  public void setDiscipline(DisciplineModel discipline) {
    this.discipline = discipline;
  }
}

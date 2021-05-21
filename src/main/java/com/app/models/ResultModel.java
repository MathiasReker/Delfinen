package com.app.models;

import java.io.Serializable;
import java.time.LocalTime;

public class ResultModel implements Serializable, Comparable<ResultModel> {

  private MemberModel member;
  private LocalTime resultTime;
  private DisciplineModel discipline;

  public ResultModel(MemberModel member, LocalTime resultTime, DisciplineModel discipline) {
    this.member = member;
    this.resultTime = resultTime;
    this.discipline = discipline;
  }

  public MemberModel getMember() {
    return member;
  }

  public void setMember(MemberModel member) {
    this.member = member;
  }

  public LocalTime getResultTime() {
    return resultTime;
  }

  public void setResultTime(LocalTime resultTime) {
    this.resultTime = resultTime;
  }

  public DisciplineModel getDiscipline() {
    return discipline;
  }

  public void setDiscipline(DisciplineModel discipline) {
    this.discipline = discipline;
  }

  @Override
  public int compareTo(ResultModel other) {
    return Integer.valueOf(this.getResultTime().compareTo(other.getResultTime()));
  }
}

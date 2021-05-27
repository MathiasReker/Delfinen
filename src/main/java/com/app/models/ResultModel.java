package com.app.models;

import java.io.Serializable;
import java.time.LocalTime;

public class ResultModel implements Serializable, Comparable<ResultModel> {
  private final LocalTime RESULT_TIME;
  private final DisciplineModel DISCIPLINE;
  private final SwimEventModel COMPETITION;
  private final String PLACEMENT;
  private MemberModel member;

  public ResultModel(
      MemberModel member,
      LocalTime resultTime,
      DisciplineModel discipline,
      SwimEventModel competition,
      String placement) {
    this.member = member;
    this.RESULT_TIME = resultTime;
    this.DISCIPLINE = discipline;
    this.COMPETITION = competition;
    this.PLACEMENT = placement;
  }

  public SwimEventModel getCompetition() {
    return COMPETITION;
  }

  public String getPlacement() {
    return PLACEMENT;
  }

  public MemberModel getMember() {
    return member;
  }

  public void setMember(MemberModel member) {
    this.member = member;
  }

  public LocalTime getResultTime() {
    return RESULT_TIME;
  }

  public DisciplineModel getDiscipline() {
    return DISCIPLINE;
  }

  @Override
  public int compareTo(ResultModel other) {
    return this.getResultTime().compareTo(other.getResultTime());
  }
}

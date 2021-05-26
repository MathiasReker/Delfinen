package com.app.models;

import java.io.Serializable;
import java.time.LocalTime;

public class ResultModel implements Serializable, Comparable<ResultModel> {
  private MemberModel member;
  private LocalTime resultTime;
  private DisciplineModel discipline;
  private SwimEventModel competition;
  private String placement;

  public ResultModel(
      MemberModel member,
      LocalTime resultTime,
      DisciplineModel discipline,
      SwimEventModel competition,
      String placement) {
    this.member = member;
    this.resultTime = resultTime;
    this.discipline = discipline;
    this.competition = competition;
    this.placement = placement;
  }

  public SwimEventModel getCompetition() {
    return competition;
  }

  public void setCompetition(SwimEventModel competition) {
    this.competition = competition;
  }

  public String getPlacement() {
    return placement;
  }

  public void setPlacement(String placement) {
    this.placement = placement;
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

  public void setResultTime(
      LocalTime resultTime) { // TODO: Method 'setResultTime(java.time.LocalTime)' is never used
    this.resultTime = resultTime;
  }

  public DisciplineModel getDiscipline() {
    return discipline;
  }

  public void setDiscipline(
      DisciplineModel
          discipline) { // TODO: Method 'setDiscipline(com.app.models.DisciplineModel)' is never
    // used
    this.discipline = discipline;
  }

  @Override
  public int compareTo(ResultModel other) {
    return this.getResultTime().compareTo(other.getResultTime());
  }
}

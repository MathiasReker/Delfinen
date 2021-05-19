package com.app.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class CompetitionModel {

  private String id;
  private LocalDate startDate;
  private String name;
  private LocalTime startTime;
  private ArrayList<ResultModel> result = new ArrayList<>();

  public CompetitionModel(LocalDate startDate, String name, LocalTime startTime) {
    setId("test"); // TODO
    this.startDate = startDate;
    this.name = name;
    this.startTime = startTime;
  }

  public CompetitionModel(String id, String name, LocalDate startDate, LocalTime startTime) {
    setId("test"); // TODO
    this.startDate = startDate;
    this.name = name;
    this.startTime = startTime;
  }

  public String getId() {
    return id;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public String getName() {
    return name;
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public ArrayList<ResultModel> getResult() {
    return result;
  }

  public void setId(
      String id) { // TODO how will we manage the ids for competitions? will need to be refactored
    this.id = id;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
  }

  public void setResult(ArrayList<ResultModel> result) {
    this.result = result;
  }

  public void addResult(ResultModel result) {
    this.result.add(result);
  }
}

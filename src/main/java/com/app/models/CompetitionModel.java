package com.app.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class CompetitionModel implements Serializable {

  private String id;
  private LocalDate startDate;
  private String name;
  private LocalTime startTime;
  private ArrayList<ResultModel> result = new ArrayList<>();

  public CompetitionModel(String id, String name, LocalDate startDate, LocalTime startTime) {
    this.id = id;
    this.startDate = startDate;
    this.name = name;
    this.startTime = startTime;
  }

  public String getId() {
    return id;
  }

  public void setId(
      String id) { // TODO how will we manage the ids for competitions? will need to be refactored
    this.id = id;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
  }

  public ArrayList<ResultModel> getResult() {
    return result;
  }

  public void setResult(ArrayList<ResultModel> result) {
    this.result = result;
  }

  public void addResult(ResultModel result) {
    this.result.add(result);
  }
}

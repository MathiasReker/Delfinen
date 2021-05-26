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
    setId(id);
    this.startDate = startDate;
    this.name = name;
    this.startTime = startTime;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public LocalDate getStartDate() { // TODO: Method 'getStartDate()' is never used
    return startDate;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalTime getStartTime() { // TODO: Method 'getStartTime()' is never used
    return startTime;
  }

  public ArrayList<ResultModel> getResult() {
    return result;
  }

  public void addResult(ResultModel result) {
    this.result.add(result);
  }
}

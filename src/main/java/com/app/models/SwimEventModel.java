package com.app.models;

import com.app.models.types.AgeGroupType;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class SwimEventModel implements Serializable {
  private String id;
  private LocalDate startDate;
  private String name;
  private LocalTime startTime;
  private ArrayList<ResultModel> result = new ArrayList<>();
  private boolean practice;
  private AgeGroupType ageGroup;

  public SwimEventModel(
      String id,
      String name,
      LocalDate startDate,
      LocalTime startTime,
      boolean practice,
      AgeGroupType ageGroup) {
    setId(id);
    this.startDate = startDate;
    this.name = name;
    this.startTime = startTime;
    this.practice = practice;
    this.ageGroup = ageGroup;
  }

  public boolean isPractice() {
    return practice;
  }

  public void setPractice(boolean practice) {
    this.practice = practice;
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

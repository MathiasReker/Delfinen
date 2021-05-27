package com.app.models;

import com.app.models.types.AgeGroupType;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class SwimEventModel implements Serializable {
  private final LocalDate START_DATE;
  private final LocalTime START_TIME;
  private final ArrayList<ResultModel> RESULTS = new ArrayList<>();
  private final AgeGroupType AGE_GROUP;
  private String id;
  private String name;
  private boolean practice;

  public SwimEventModel(
      String id,
      String name,
      LocalDate startDate,
      LocalTime startTime,
      boolean practice,
      AgeGroupType ageGroup) {
    setId(id);
    this.START_DATE = startDate;
    this.name = name;
    this.START_TIME = startTime;
    this.practice = practice;
    this.AGE_GROUP = ageGroup;
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
    return START_DATE;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalTime getStartTime() { // TODO: Method 'getStartTime()' is never used
    return START_TIME;
  }

  public ArrayList<ResultModel> getResult() {
    return RESULTS;
  }

  public void addResult(ResultModel result) {
    this.RESULTS.add(result);
  }
}

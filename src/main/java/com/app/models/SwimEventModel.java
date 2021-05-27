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
  private final boolean PRACTICE;
  private String id;
  private String name;

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
    this.PRACTICE = practice;
    this.AGE_GROUP = ageGroup;
  }

  public boolean isPractice() {
    return PRACTICE;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public LocalDate getStartDate() {
    return START_DATE;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalTime getStartTime() {
    return START_TIME;
  }

  public ArrayList<ResultModel> getResult() {
    return RESULTS;
  }

  public void addResult(ResultModel result) {
    this.RESULTS.add(result);
  }
}

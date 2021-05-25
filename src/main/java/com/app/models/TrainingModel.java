package com.app.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class TrainingModel implements Serializable {

  private String id;
  private LocalDate trainingDate;
  private ArrayList<ResultModel> result = new ArrayList<>();

  public TrainingModel(String id, LocalDate trainingDate) {
    this.id = id;
    this.trainingDate = trainingDate;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public LocalDate getTrainingDate() {
    return trainingDate;
  }

  public void setTrainingDate(LocalDate trainingDate) {
    this.trainingDate = trainingDate;
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

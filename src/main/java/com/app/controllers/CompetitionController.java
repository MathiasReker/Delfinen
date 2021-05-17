package com.app.controllers;

import com.app.models.CompetitionModel;
import com.app.models.ResultModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class CompetitionController {

  ArrayList<CompetitionModel> competitions;

  public void createNewCompetition() {
    // TODO we need to get some clarification regarding how we read input
  }

  /**
   * a method that returns a competition based on the ID provided
   *
   * @param id is the competition id for the competition you wish to return
   * @return a competition based on the id that is provided
   */
  public CompetitionModel getCompetition(String id) {

    for (CompetitionModel competition : competitions) {
      if (id.equals(competition.getId())) {
        return competition;
      }
    }
    return null;
  }

  /**
   * A method that adds a result, to a given competition
   *
   * @param competition the competition you wish to add the result to
   * @param resultModel the result you wish to add
   */
  public void addResultToCompetition(CompetitionModel competition, ResultModel resultModel) {
    competition.addResult(resultModel);
  }

  /**
   * A method to validate that the date input we receive is a valid format
   *
   * @param dateString a String with the date that needs to be parsed
   * @return a date as a LocalDate type
   */
  public LocalDate validDate(String dateString) {

    String dateFormat = "dd-MM-yyyy";
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat);

    LocalDate date = null;

    try {
      date = LocalDate.parse(dateString, dateFormatter);
    } catch (DateTimeParseException e) {
      e.printStackTrace();
    }
    return date;
  }

  /**
   * A method to validate that the time input we receive is a valid format
   *
   * @param timeString a String with the time that needs to be parsed
   * @return returns a time as a LocalTime type
   */
  public LocalTime validTime(String timeString) {

    String timeFormat = "mm:ss";
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(timeFormat);

    LocalTime time = null;

    try {
      time = LocalTime.parse(timeString, dateFormatter);
    } catch (DateTimeParseException e) {
      e.printStackTrace();
    }
    return time;
  }
}

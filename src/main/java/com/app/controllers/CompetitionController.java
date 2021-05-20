package com.app.controllers;

import com.app.models.*;
import com.app.models.services.CompetitionService;
import com.app.views.CompetitionView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class CompetitionController {

  private ArrayList<CompetitionModel> competitions = new ArrayList<>();
  private final CompetitionView VIEW = new CompetitionView();
  private CompetitionService competitionService;

  public CompetitionController() {
    try {
      competitionService = new CompetitionService("data/competitions.txt");
      competitions = competitionService.getCompetitionsFromFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void createNewCompetition(Scanner in)  {

    VIEW.printInline("Please enter competition name: ");
    String competitionName = in.nextLine();
    VIEW.printInline("Please enter date: ");
    LocalDate date = LocalDate.parse(validateDate(in), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    VIEW.printInline("Please enter start time of the competition: ");
    LocalTime startTime = validCompetitionTime(in.nextLine());
    competitions.add(new CompetitionModel(date, competitionName, startTime));

    try {
      competitionService.saveCompetitionsToFile(competitions);
      VIEW.print("Competition was added!");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void createResultToCompetition(Scanner in) {

    VIEW.printInline(
        "Which competition do you wish to add a result for, please enter Competition ID: ");
    CompetitionModel competition = getCompetition(in.nextLine());
    VIEW.printInline("Which member do you which to add a result for, please enter member ID: ");
    MemberModel member = new MemberModel();
    VIEW.displayMenu(styleToArray());
    int styleChoice = in.nextInt();
    in.nextLine();

    VIEW.displayMenu(distanceToArray());
    int distanceChoice = in.nextInt();
    in.nextLine();

    VIEW.printInline("How fast was is completed: ");
    LocalTime time = LocalTime.parse("10:00");

    DisciplineModel disciplineModel =
        new DisciplineModel(DistanceModel.valueOf(distanceToArray()[distanceChoice-1]).getMeters(), styleToArray()[styleChoice-1]) ;

    addResultToCompetition(competition, new ResultModel(member, time, disciplineModel));
  }

  /**
   * Returns a competition based on the provided ID.
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

  public String [] viewCompetitionResults(Scanner in){

    VIEW.printInline("Which competition results do you wish to view, please  enter competition ID: ");
    CompetitionModel competition = getCompetition(in.nextLine());
    ArrayList<ResultModel> resultsOfCompetition = competition.getResult();
    String [] resultsToString = new String[resultsOfCompetition.size()];

    for (int i = 0; i < resultsOfCompetition.size(); i++) {
      String name = resultsOfCompetition.get(i).getMember().getName();
      String style = resultsOfCompetition.get(i).getDiscipline().getStyle();
      String distance = Integer.toString(resultsOfCompetition.get(i).getDiscipline().getDistance());
      String completionTime = resultsOfCompetition.get(i).getResultTime().toString();
      resultsToString[i] = String.join(";", name,style,distance,completionTime);
    }
    return resultsToString;
  }
  
  public MemberModel getMember(String id){

    // TODO create a method to get a member based on an ID
    return null;
  }

  /**
   * Add the result to a given competition.
   *
   * @param competition the competition you wish to add the result to
   * @param resultModel the result you wish to add
   */
  public void addResultToCompetition(CompetitionModel competition, ResultModel resultModel) {
    competition.addResult(resultModel);
  }

  public String[] styleToArray() {
    String[] result = new String[StyleModel.values().length];

    for (int i = 0; i < result.length; i++) {
      result[i] = StyleModel.values()[i].name();
    }
    return result;
  }

  public String[] distanceToArray() {
    String[] result = new String[DistanceModel.values().length];

    for (int i = 0; i < result.length; i++) {
      result[i] = DistanceModel.values()[i].name();
    }
    return result;
  }

  private String validateDate(Scanner in) {

    String result = in.nextLine();
    while (!ValidateModel.isValidDate(result)) {
      VIEW.printInlineWarning("Not a valid date. Please try again: ");
      result = in.nextLine();
    }

    return result;
  }

  /**
   * A method to validate that the time input we receive is a valid format
   *
   * @param timeString a String with the time that needs to be parsed.
   * @return returns a time as a LocalTime type
   */
  public LocalTime validResultTime(String timeString) {

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

  public LocalTime validCompetitionTime(String timeString) {

    String timeFormat = "HH:mm";
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

package com.app.controllers;

import com.app.models.*;
import com.app.models.services.CompetitionService;
import com.app.views.CompetitionView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class CompetitionController {

  private ArrayList<CompetitionModel> competitions = new ArrayList<>();
  private final CompetitionView VIEW = new CompetitionView();
  private CompetitionService competitionService;

  public CompetitionController() {
    try {
      competitionService = new CompetitionService("data/competitions.bin");
      competitions = toArraylist(competitionService.getCompetitionsFromFile());
    } catch (IOException | ClassNotFoundException e) {
      VIEW.printWarning("Could not load Competitions");
    }
  }

  public ArrayList<CompetitionModel> getCompetitions() {
    return competitions;
  }

  public void createNewCompetition(Scanner in) {

    VIEW.printInline("Please enter competition name: ");
    String competitionName = in.nextLine();
    VIEW.printInline("Please enter date [dd/MM/yyyy]: ");
    LocalDate date = LocalDate.parse(validateDate(in), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    VIEW.printInline("Please enter start time of the competition [HH:mm]: ");
    LocalTime startTime =
        LocalTime.parse(validCompetitionTime(in), DateTimeFormatter.ofPattern("HH:mm"));
    competitions.add(new CompetitionModel(generateID(), competitionName, date, startTime));

    VIEW.printSuccess("New Competition created!");

    saveCompetitionsToFile();
  }

  public void addResultToCompetition(Scanner in) {

    VIEW.printInline("Please enter Competition ID: ");
    CompetitionModel competition = getCompetition(in);
    VIEW.printInline("Please enter member ID: ");
    MemberModel member = getMember(in.nextLine());
    VIEW.displayMenu(styleToArray());
    int styleChoice = in.nextInt();
    in.nextLine();

    VIEW.displayMenu(distanceToArray(StyleModel.values()[styleChoice - 1].name(), member));
    int distanceChoice = in.nextInt();
    in.nextLine();

    VIEW.printInline("Enter result [mm:ss:SS]: ");
    LocalTime time =
        LocalTime.parse("00:" + validResultTime(in), DateTimeFormatter.ofPattern("HH:mm:ss:SS"));

    DisciplineModel disciplineModel =
        new DisciplineModel(
            DistanceModel.values()[distanceChoice - 1].getMeters(),
            styleToArray()[styleChoice - 1]);

    addResultToCompetition(competition, new ResultModel(member, time, disciplineModel));
    VIEW.printSuccess("New result was added!");
  }

  /**
   * Returns a competition based on the provided ID.
   *
   * @param in is the competition id for the competition you wish to return
   * @return a competition based on the id that is provided
   */
  public CompetitionModel getCompetition(Scanner in) {

    String input = in.nextLine();
    while (!isValidCompetitionId(input)) {
      VIEW.printInlineWarning("Not a valid ID. Please try again: ");
      input = in.nextLine();
    }
    for (CompetitionModel competition : competitions) {
      if (input.equals(competition.getId())) {
        return competition;
      }
    }
    return null;
  }

  public boolean isValidCompetitionId(String id) {
    for (CompetitionModel competition : competitions) {
      if (id.equals(competition.getId())) {
        return true;
      }
    }
    return false;
  }

  public void viewCompetitionResults(Scanner in) {

    VIEW.printInline("Please  enter competition ID: ");
    CompetitionModel competition = getCompetition(in);
    ArrayList<ResultModel> resultsOfCompetition = competition.getResult();
    String[] resultsToString = new String[resultsOfCompetition.size()];

    for (int i = 0; i < resultsOfCompetition.size(); i++) {
      String name = resultsOfCompetition.get(i).getMember().getName();
      String style = resultsOfCompetition.get(i).getDiscipline().getStyle();
      String distance = Integer.toString(resultsOfCompetition.get(i).getDiscipline().getDistance());
      String completionTime = resultsOfCompetition.get(i).getResultTime().toString();
      resultsToString[i] = String.join(";", name, style, distance, completionTime);
    }
    VIEW.displayCompetitionResults(resultsToString);
  }

  public MemberModel getMember(String id) {
    MemberController memberController = new MemberController();
    MemberModel memberModel = null;
    try {
      memberModel = memberController.getMemberByID(id);
    } catch (MemberNotFoundException e) {
      e.printStackTrace();
    }
    return memberModel;
  }

  /**
   * Add the result to a given competition.
   *
   * @param competition the competition you wish to add the result to
   * @param resultModel the result you wish to add
   */
  public void addResultToCompetition(CompetitionModel competition, ResultModel resultModel) {
    competition.addResult(resultModel);
    saveCompetitionsToFile();
  }

  public String[] styleToArray() {
    String[] result = new String[StyleModel.values().length];

    for (int i = 0; i < result.length; i++) {
      result[i] = StyleModel.values()[i].name();
    }
    return result;
  }

  public String[] distanceToArray(String style, MemberModel member) {
    DisciplinesController disciplinesController = new DisciplinesController();
    ArrayList<DisciplineModel> disciplineModels = disciplinesController.chosenDiscipline(1, style);
    String[] result = new String[disciplineModels.size()];

    for (int i = 0; i < result.length; i++) {
      result[i] = String.valueOf(disciplineModels.get(i).getDistance());
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
   * @param in a String with the time that needs to be parsed.
   * @return returns a time as a LocalTime type
   */
  private String validResultTime(Scanner in) {
    String result = in.nextLine();
    while (!ValidateModel.isValidResultTime("00:" + result)) {
      VIEW.printInlineWarning("Not a valid time. Please try again: ");
      result = in.nextLine();
    }

    return result;
  }

  public String validCompetitionTime(Scanner in) {
    String result = in.nextLine();
    while (!ValidateModel.isValidCompetitionTime(result)) {
      VIEW.printInlineWarning("Not a valid time. Please try again: ");
      result = in.nextLine();
    }

    return result;
  }

  public void saveCompetitionsToFile() {

    competitionService.saveCompetitionsToFile(competitions.toArray(new CompetitionModel[0]));
  }

  private ArrayList<CompetitionModel> toArraylist(CompetitionModel[] competitions) {
    ArrayList<CompetitionModel> result = new ArrayList<>();
    for (CompetitionModel c : competitions) {
      result.add(c);
    }
    return result;
  }

  private String generateID() { // TODO refactor to valuof
    int id;
    try {
      int temp = Integer.parseInt(competitions.get(competitions.size() - 1).getId());
      id = temp + 1;
    } catch (IndexOutOfBoundsException e) {
      id = 1;
    }
    return Integer.toString(id);
  }
}

package com.app.controllers;

import com.app.controllers.utils.Input;
import com.app.models.*;
import com.app.models.services.CompetitionService;
import com.app.models.services.ConfigService;
import com.app.views.CompetitionView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class CompetitionController {
  private final CompetitionView VIEW = new CompetitionView();
  private ArrayList<CompetitionModel> competitions = new ArrayList<>();
  private CompetitionService competitionService;

  public CompetitionController() {
    try {
      competitionService = new CompetitionService(new ConfigService("competitionsBin").getPath());
      competitions = toArraylist(competitionService.getCompetitionsFromFile());
    } catch (IOException | ClassNotFoundException e) {
      VIEW.printWarning(e.getMessage());
    }
  }

  public ArrayList<CompetitionModel> getCompetitions() {
    return competitions;
  }

  public void createNewCompetition(Scanner in) {
    VIEW.printInline("Name of competition: ");
    String competitionName = in.nextLine(); // TODO: Validate

    VIEW.printInline("Date [dd/MM/yyyy]: ");
    LocalDate date =
        LocalDate.parse(Input.validateDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    VIEW.printInline("Please enter start time of the competition [HH:mm]: ");
    LocalTime startTime =
        LocalTime.parse(Input.validateCompetitionTime(), DateTimeFormatter.ofPattern("HH:mm"));
    competitions.add(new CompetitionModel(generateId(), competitionName, date, startTime));

    VIEW.printSuccess("Competition successfully created.");

    saveCompetitionsToFile();
  }

  public void addResultToCompetition(Scanner in) {
    VIEW.printInline("Competition ID: ");
    CompetitionModel competition = getCompetition(in); // TODO: Validate

    VIEW.printInline("Member ID: ");
    MemberModel member = getMember(in.nextLine()); // TODO: Validate

    VIEW.displayOptions(styleToArray());
    int styleChoice = Input.validateOptionRange(styleToArray().length);

    String[] strings =
        distanceToArray(StyleModel.values()[styleChoice - 1].name(), member.getGender());
    VIEW.displayOptions(strings);
    int distanceChoice = in.nextInt(strings.length);

    VIEW.printInline("Result time [mm:ss:SS]: ");
    LocalTime time =
        LocalTime.parse(
            "00:" + Input.validateCompetitionResultTime(),
            DateTimeFormatter.ofPattern("HH:mm:ss:SS"));

    DisciplineModel disciplineModel =
        new DisciplineModel(
            DistanceModel.values()[distanceChoice - 1].getMeters(),
            styleToArray()[styleChoice - 1]);

    addResultToCompetition(competition, new ResultModel(member, time, disciplineModel));
    VIEW.printSuccess("Result successfully added.");
  }

  /**
   * Returns a competition based on the provided ID.
   *
   * @param in is the competition id for the competition you wish to return
   * @return a competition based on the id that is provided
   */
  public CompetitionModel getCompetition(Scanner in) { // TODO: Move to ValidateModel
    String input = in.nextLine();
    while (!ValidateModel.isValidCompetitionId(competitions, input)) {
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

  public void viewCompetitionResults(Scanner in) {
    VIEW.printInline("Competition ID: ");
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
    MemberModel result = null;
    try {
      result = memberController.getMemberByID(id);
    } catch (MemberNotFoundException e) {
      e.printStackTrace();
    }

    return result;
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

  public String[] distanceToArray(String style, GenderModel gender) {
    DisciplinesController disciplinesController = new DisciplinesController();
    ArrayList<DisciplineModel> disciplineModels =
        disciplinesController.chosenDiscipline(gender.name(), style);
    String[] result = new String[disciplineModels.size()];

    for (int i = 0; i < result.length; i++) {
      result[i] = String.valueOf(disciplineModels.get(i).getDistance());
    }

    return result;
  }

  public void saveCompetitionsToFile() {
    try {
      competitionService.saveCompetitionsToFile(competitions.toArray(new CompetitionModel[0]));
    } catch (IOException e) {
      VIEW.printWarning(e.getMessage());
    }
  }

  private ArrayList<CompetitionModel> toArraylist(CompetitionModel[] competitions) {
    ArrayList<CompetitionModel> result = new ArrayList<>();
    Collections.addAll(result, competitions);

    return result;
  }

  private String generateId() {
    int oldId = 0;
    if (competitions.size() > 0) {
      oldId = Integer.parseInt(competitions.get(competitions.size() - 1).getId());
    }
    int newId = oldId + 1;

    return String.valueOf(newId);
  }
}

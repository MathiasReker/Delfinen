package com.app.controllers;

import com.app.models.CompetitionModel;
import com.app.models.DisciplineModel;
import com.app.models.MemberModel;
import com.app.models.ResultModel;
import com.app.models.exceptions.MemberNotFoundException;
import com.app.models.services.CompetitionService;
import com.app.models.services.ConfigService;
import com.app.models.types.DistanceType;
import com.app.models.types.GenderType;
import com.app.models.types.StyleType;
import com.app.views.CompetitionView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class CompetitionController {
  private final CompetitionView VIEW = new CompetitionView();
  private ArrayList<CompetitionModel> competitions;
  private CompetitionService competitionService;
  private final MemberController MEMBER_CONTROLLER = new MemberController();

  public CompetitionController() {
    try {
      competitionService = new CompetitionService(new ConfigService("competitionsBin").getPath());
      competitions = toArraylist(competitionService.getCompetitionsFromFile());
    } catch (IOException | ClassNotFoundException e) {
      VIEW.printWarning("Could not load any competitions.");
      competitions = new ArrayList<>();
    }
  }

  public ArrayList<CompetitionModel> getCompetitions() {
    return competitions;
  }

  public void createNewCompetition() {
    VIEW.printInline("Name of competition: ");
    String competitionName = InputController.anyString();

    VIEW.printInline("Date [dd/MM/yyyy]: ");
    LocalDate date =
        LocalDate.parse(InputController.validateDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    VIEW.printInline("Please enter start time of the competition [HH:mm]: ");
    LocalTime startTime =
        LocalTime.parse(
            InputController.validateCompetitionTime(), DateTimeFormatter.ofPattern("HH:mm"));
    competitions.add(new CompetitionModel(generateId(), competitionName, date, startTime));

    VIEW.printSuccess("Competition successfully created.");

    saveCompetitions();
  }

  public void addResultToCompetition() {
    if (competitions.isEmpty()) {
      VIEW.printWarning("No competitions available.");
    } else {
    viewTableCompetitions();
    VIEW.printInline("Competition ID: ");
    CompetitionModel competition = InputController.validateCompetitionsId(competitions);

    MEMBER_CONTROLLER.viewTableMembers();
    VIEW.printInline("Member ID: ");
    MemberModel member =
        getMember(InputController.validateMemberId(new MemberController().getMembers()));
    do {
      addResultTime(member, competition);
      VIEW.printInline("Do you wish to add another result to this member [Y/n]: ");
    } while (InputController.promptYesNo());
  }
  }

  public void addResultTime(MemberModel member, CompetitionModel competition) {
    VIEW.displayOptions(styleToArray());
    int styleChoice = InputController.validateOptionRange(styleToArray().length);

    String[] distances = distanceToArray(StyleType.values()[styleChoice - 1], member.getGender());
    VIEW.displayOptions(distances);
    int distanceChoice = InputController.validateOptionRange(distances.length);

    VIEW.printInline("Result time [mm:ss:SS]: ");
    LocalTime time =
        LocalTime.parse(
            "00:" + InputController.validateCompetitionResultTime(),
            DateTimeFormatter.ofPattern("HH:mm:ss:SS"));


    VIEW.printInline("Placement: ");
    String placement = InputController.validatePlacement();

    DisciplineModel disciplineModel =
        new DisciplineModel(
            DistanceType.values()[distanceChoice - 1], StyleType.values()[styleChoice - 1]);

    addResultToCompetition(
        competition, new ResultModel(member, time, disciplineModel, competition, placement));
    VIEW.printSuccess("Result successfully added.");
  }

  public void viewCompetitionResults() {
    if (competitions.isEmpty()) {
      VIEW.print("No competitions available.");
    } else {
      VIEW.printInline("Competition ID: ");
      CompetitionModel competition = InputController.validateCompetitionsId(competitions);

      ArrayList<ResultModel> resultsOfCompetition = competition.getResult();

      VIEW.displayCompetitionResults(arrayWithResultToDisplay(resultsOfCompetition));
    }
  }

  private String[][] arrayWithResultToDisplay(ArrayList<ResultModel> resultTimes) {
    String[][] result = new String[resultTimes.size()][4];

    for (int i = 0; i < resultTimes.size(); i++) {
      ResultModel resultModel = resultTimes.get(i);

      String name = resultModel.getMember().getName();
      String style = resultModel.getDiscipline().getStyle().name();
      String distance = Integer.toString(resultModel.getDiscipline().getDistance().getMeters());
      String completionTime = resultModel.getResultTime().toString();

      result[i] = new String[] {name, style, distance, completionTime};
    }

    return result;
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
    saveCompetitions();
  }

  public String[] styleToArray() {
    String[] result = new String[StyleType.values().length];

    for (int i = 0; i < result.length; i++) {
      result[i] = StyleType.values()[i].name();
    }

    return result;
  }

  public String[] distanceToArray(StyleType style, GenderType gender) {
    DisciplinesController disciplinesController = new DisciplinesController();
    ArrayList<DisciplineModel> disciplineModels =
        disciplinesController.chosenDiscipline(gender, style);
    String[] result = new String[disciplineModels.size()];

    for (int i = 0; i < result.length; i++) {
      result[i] = String.valueOf(disciplineModels.get(i).getDistance());
    }

    return result;
  }

  public void saveCompetitions() {
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
    if (!competitions.isEmpty()) {
      oldId = Integer.parseInt(competitions.get(competitions.size() - 1).getId());
    }
    int newId = oldId + 1;

    return String.valueOf(newId);
  }

  private String[] getCompetitionHeader() {
    return new String[] {"ID", "Name", "Date", "Start time"};
  }

  public int[] getColumnWidth() {
    int[] result = new int[getCompetitionHeader().length];

    for (CompetitionModel competiton : competitions) {
      String[] body = getCompetitionLine(competiton);

      for (int i = 0; i < body.length; i++) {
        if (body[i] == null) {
          body[i] = "--";
        }

        if (body[i].length() > result[i]) {
          result[i] = body[i].length();
        }
      }
    }

    return result;
  }
  private String[] getCompetitionLine(CompetitionModel competition) {
    return new String[] {
        competition.getId(),
        competition.getName(),
        String.valueOf(competition.getStartDate()),
        String.valueOf(competition.getStartTime())
    };
  }

  public void viewTableCompetitions() {
    if (competitions.isEmpty()) {
      VIEW.printWarning("No competitions.");
    } else {
      String[] header = getCompetitionHeader();
      VIEW.displayMember(header, getColumnWidth());

      for (CompetitionModel competition : competitions) {
        String[] body = getCompetitionLine(competition);
        VIEW.displayMember(body, getColumnWidth());
      }
    }
  }
}

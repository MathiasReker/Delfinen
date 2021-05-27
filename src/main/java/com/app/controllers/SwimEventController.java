package com.app.controllers;

import com.app.models.MemberModel;
import com.app.models.ResultModel;
import com.app.models.SwimEventModel;
import com.app.models.exceptions.MemberNotFoundException;
import com.app.models.services.ConfigService;
import com.app.models.services.SwimEventService;
import com.app.models.types.AgeGroupType;
import com.app.views.SwimEventView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class SwimEventController {
  private final SwimEventView VIEW = new SwimEventView();
  private final MemberController MEMBER_CONTROLLER = new MemberController();
  private ArrayList<SwimEventModel> swimEventModels;
  private SwimEventService swimEventService;

  public SwimEventController() {
    try {
      swimEventService = new SwimEventService(new ConfigService("swimeventBin").getPath());
      swimEventModels = toArraylist(swimEventService.load());
    } catch (IOException | ClassNotFoundException e) {
      VIEW.printWarning("Could not load any swim events.");
      swimEventModels = new ArrayList<>();
    }
  }

  public ArrayList<SwimEventModel> getSwimEventModels() {
    return swimEventModels;
  }

  /** Create a new competition, uses input controller to get input from user */
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

    VIEW.printInline("Junior or senior [j/s]: "); // TODO change to display with array.
    AgeGroupType ageGroup = findAgeGroup(InputController.validateAgeGroup());

    swimEventModels.add(
        new SwimEventModel(generateId(), competitionName, date, startTime, false, ageGroup));

    VIEW.printSuccess("Competition successfully created.");

    saveSwimEvents();
  }

  public void createNewPractice() {

    VIEW.printInline("Date [dd/MM/yyyy]: ");
    LocalDate date =
        LocalDate.parse(InputController.validateDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    VIEW.printInline("Please enter start time of the practice [HH:mm]: ");
    LocalTime startTime =
        LocalTime.parse(
            InputController.validateCompetitionTime(), DateTimeFormatter.ofPattern("HH:mm"));

    VIEW.printInline("Junior or senior [j/s]: ");
    AgeGroupType ageGroup = findAgeGroup(InputController.validateAgeGroup());

    String name = ageGroup.name() + " practice " + date + " " + startTime;

    swimEventModels.add(new SwimEventModel(generateId(), name, date, startTime, true, ageGroup));

    VIEW.printSuccess("Practice successfully created.");

    saveSwimEvents();
  }

  private AgeGroupType findAgeGroup(String ageGroup) {
    if (ageGroup.equalsIgnoreCase("j")) {
      return AgeGroupType.JUNIOR;
    } else {
      return AgeGroupType.SENIOR;
    }
  }

  /**
   * Adds a result to a competition if the competition exists, uses input controller to get input
   * from user Uses addResultTime to add a time to the competition
   */
  public void addResultToCompetition() {
    ResultController resultController = new ResultController();

    if (swimEventModels.isEmpty()) {
      VIEW.printWarning("No swim events available.");
    } else {
      viewTableCompetitions();
      VIEW.printInline("Competition ID: ");
      SwimEventModel competition = InputController.validateSwimEventId(swimEventModels);

      do {
        MEMBER_CONTROLLER.viewTableMembers();
        VIEW.printInline("Member ID: ");
        MemberModel member =
            getMember(InputController.validateMemberId(new MemberController().getMembers()));
        do {
          addResultToCompetition(competition, resultController.addResultTime(member, competition));
          VIEW.printInline("Do you wish to add another result to this member [Y/n]: ");
        } while (InputController.promptYesNo());
        VIEW.printInline(
            "Do you wish to add results for another member, on this competition [Y/n]: ");
      } while (InputController.promptYesNo());
    }
  }

  public void addResultToPractice() {
    ResultController resultController = new ResultController();

    if (swimEventModels.isEmpty()) {
      VIEW.printWarning("No swim events available.");
    } else {
      viewTablePractice();
      VIEW.printInline("Practice ID: ");
      SwimEventModel practice = InputController.validateSwimEventId(swimEventModels);

      do {
        MEMBER_CONTROLLER.viewTableMembers();
        VIEW.printInline("Member ID: ");
        MemberModel member =
            getMember(InputController.validateMemberId(new MemberController().getMembers()));
        do {
          addResultToCompetition(practice, resultController.addResultTime(member, practice));
          VIEW.printInline("Do you wish to add another result to this member [Y/n]: ");
        } while (InputController.promptYesNo());
        VIEW.printInline("Do you wish to add results for another member, on this practice [Y/n]: ");

      } while (InputController.promptYesNo());
    }
  }

  /** View competition results based on a swim event id */
  public void viewCompetitionResults() {
    if (swimEventModels.isEmpty()) {
      VIEW.print("No swim events available.");
    } else {
      VIEW.printInline("Competition ID: ");
      SwimEventModel competition = InputController.validateSwimEventId(swimEventModels);
      // Todo refactor to have validation of competion closer

      ArrayList<ResultModel> resultsOfCompetition = competition.getResult();

      VIEW.displayCompetitionResults(arrayWithResultToDisplay(resultsOfCompetition));
    }
  }

  /** View practice results based on a swim event id */
  public void viewPracticeResults() {
    if (swimEventModels.isEmpty()) {
      VIEW.print("No swim events available.");
    } else {
      VIEW.printInline("Practice ID: ");
      SwimEventModel practice = InputController.validateSwimEventId(swimEventModels);
      // Todo refactor to have validation of competion closer

      ArrayList<ResultModel> resultsOfCompetition = practice.getResult();

      VIEW.displayCompetitionResults(arrayWithResultToDisplay(resultsOfCompetition));
    }
  }

  /**
   * Converts and array of result models to a 2d String array
   *
   * @param resultTimes Arraylist of result times, that needs to be converted to a string
   * @return A 2d String array
   */
  private String[][] arrayWithResultToDisplay(ArrayList<ResultModel> resultTimes) {
    String[][] result = new String[resultTimes.size()][4]; // TODO change to softcode

    for (int i = 0; i < resultTimes.size(); i++) {
      ResultModel resultModel = resultTimes.get(i);

      String name = resultModel.getMember().getName();
      String style = resultModel.getDiscipline().getStyle().name();
      String distance = Integer.toString(resultModel.getDiscipline().getDistance().getMeters());
      String completionTime = resultModel.getResultTime().toString();

      result[i] = new String[] {name, style, distance, completionTime}; // TODO add if practice
    }

    return result;
  }

  /**
   * @param id of the member that needs to be returned
   * @return a member based on id, if it exists
   */
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
   * Add the result to a given swim events.
   *
   * @param swimEventModel the swim event you wish to add the result to
   * @param resultModel the result you wish to add
   */
  public void addResultToCompetition(SwimEventModel swimEventModel, ResultModel resultModel) {
    swimEventModel.addResult(resultModel);
    saveSwimEvents();
  }

  /** Saves the swim events to a file */
  public void saveSwimEvents() {
    try {
      swimEventService.save(swimEventModels.toArray(new SwimEventModel[0]));
    } catch (IOException e) {
      VIEW.printWarning(e.getMessage());
    }
  }

  /**
   * Converts an array to an arraylist, this is used for loading the swim events into the program
   *
   * @param swimEventModels the competitions that need to be converted
   * @return a arraylist of competitions
   */
  private ArrayList<SwimEventModel> toArraylist(SwimEventModel[] swimEventModels) {
    ArrayList<SwimEventModel> result = new ArrayList<>();
    Collections.addAll(result, swimEventModels);

    return result;
  }

  /**
   * Generates the available ID
   *
   * @return a String of the next available ID
   */
  private String generateId() {
    int oldId = 0;
    if (!swimEventModels.isEmpty()) {
      oldId = Integer.parseInt(swimEventModels.get(swimEventModels.size() - 1).getId());
    }
    int newId = oldId + 1;

    return String.valueOf(newId);
  }

  /** @return a Stringarray of the swim event header */
  private String[] getSwimEventHeader() {
    return new String[] {"ID", "Name", "Date", "Start time"};
  }

  /** @return a int array with the columwidth */
  public int[] getColumnWidth() {
    int[] result = new int[getSwimEventHeader().length];

    for (SwimEventModel swimEventModel : swimEventModels) {
      String[] body = getSwimEventLine(swimEventModel);

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

  /**
   * Converts a swim event into a String array
   *
   * @param swimEventModel that needs to be converted to a String Array
   * @return the the swim event as a String array
   */
  private String[] getSwimEventLine(SwimEventModel swimEventModel) {
    return new String[] {
      swimEventModel.getId(),
      swimEventModel.getName(),
      String.valueOf(swimEventModel.getStartDate()),
      String.valueOf(swimEventModel.getStartTime())
    };
  }

  /** creates a view for the user of the competitions available */
  public void viewTableCompetitions() {
    if (swimEventModels.isEmpty()) {
      VIEW.printWarning("No swim events.");
    } else {
      String[] header = getSwimEventHeader();
      VIEW.displayCompetition(header, getColumnWidth());

      for (SwimEventModel competition : swimEventModels) {
        if (!competition.isPractice()) {
          String[] body = getSwimEventLine(competition);
          VIEW.displayCompetition(body, getColumnWidth());
        }
      }
    }
  }

  /** creates a view for the user of the practice available */
  public void viewTablePractice() {
    if (swimEventModels.isEmpty()) {
      VIEW.printWarning("No swim events.");
    } else {
      String[] header = getSwimEventHeader();
      VIEW.displayCompetition(header, getColumnWidth());

      for (SwimEventModel practice : swimEventModels) {
        if (practice.isPractice()) {
          String[] body = getSwimEventLine(practice);
          VIEW.displayCompetition(body, getColumnWidth());
        }
      }
    }
  }
}

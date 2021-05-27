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

  /**
   * Loads swim events.
   *
   * @auther Mohamad
   */
  public SwimEventController() {
    try {
      swimEventService = new SwimEventService(new ConfigService("swimEventBin").getPath());
      swimEventModels = toArraylist(swimEventService.load());
    } catch (IOException | ClassNotFoundException e) {
      VIEW.printWarning("Could not load any swim events.");
      swimEventModels = new ArrayList<>();
    }
  }

  ArrayList<SwimEventModel> getSwimEventModels() {
    return swimEventModels;
  }

  /**
   * Create new competition based on user input.
   *
   * @auther Jackie, Mohamad
   */
  public void createNewCompetition() {
    VIEW.printInline("Name of competition: ");
    String competitionName = InputController.anyString();

    VIEW.printInline("Date [dd/MM/yyyy]: ");
    LocalDate date =
        LocalDate.parse(InputController.validateDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    VIEW.printInline("Please enter start time of the competition [HH:mm]: ");
    LocalTime startTime =
        LocalTime.parse(
            InputController.validateSwimEventTime(), DateTimeFormatter.ofPattern("HH:mm"));

    VIEW.printInline("Junior or senior [j/s]: "); // TODO change to display with array.
    AgeGroupType ageGroup = getAgeGroup(InputController.validateAgeGroup());

    swimEventModels.add(
        new SwimEventModel(
            generateSwimEventId(), competitionName, date, startTime, false, ageGroup));

    VIEW.printSuccess("Competition successfully created.");

    saveSwimEvents();
  }

  /**
   * Create new practice.
   *
   * @auther Jackie, Mohamed
   */
  public void createNewPractice() {
    VIEW.printInline("Date [dd/MM/yyyy]: ");
    LocalDate date =
        LocalDate.parse(InputController.validateDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    VIEW.printInline("Please enter start time of the practice [HH:mm]: ");
    LocalTime startTime =
        LocalTime.parse(
            InputController.validateSwimEventTime(), DateTimeFormatter.ofPattern("HH:mm"));

    VIEW.printInline("Junior or senior [j/s]: "); // TODO change to display with array.
    AgeGroupType ageGroup = getAgeGroup(InputController.validateAgeGroup());

    String name = ageGroup.name() + " practice " + date + " " + startTime;

    swimEventModels.add(
        new SwimEventModel(generateSwimEventId(), name, date, startTime, true, ageGroup));

    VIEW.printSuccess("Practice successfully created.");

    saveSwimEvents();
  }

  /**
   * Get age group.
   *
   * @param ageGroup String
   * @return AgeGroupType
   * @auther Jackie
   */
  private AgeGroupType getAgeGroup(String ageGroup) {
    if (ageGroup.equalsIgnoreCase("j")) {
      return AgeGroupType.JUNIOR; // TODO: refactor -> j ..
    } else {
      return AgeGroupType.SENIOR;
    }
  }

  /**
   * Adds result(s) to a competition if the competition exists based on user input.
   *
   * @auther Jackie, Mohamad
   */
  public void addResultToCompetition() {
    ResultController resultController = new ResultController();

    if (swimEventModels.isEmpty()) {
      VIEW.printWarning("No swim events available.");
    } else {
      viewTableCompetitions();
      VIEW.printInline("Competition ID: ");
      SwimEventModel competition = InputController.validateSwimEvent(swimEventModels);

      if (competition != null && !competition.isPractice()) {
        do {
          MEMBER_CONTROLLER.viewTableMembers();
          VIEW.printInline("Member ID: ");

          try {
            MemberModel member =
                MEMBER_CONTROLLER.getMemberById(
                    InputController.validateMemberId(MEMBER_CONTROLLER.getMembers()));
            do {
              addResultToSwimEvent(
                  competition, resultController.addResultTime(member, competition));
              VIEW.printInline("Add another result to this member [Y/n]: ");
            } while (InputController.promptYesNo());
          } catch (MemberNotFoundException e) {
            VIEW.printWarning(e.getMessage());
          }

          VIEW.printInline("Add results for another member, on this competition [Y/n]: ");
        } while (InputController.promptYesNo());
      } else {
        VIEW.printWarning("Not a valid choice.");
      }
    }
  }

  /**
   * Add result to practice based on user input.
   *
   * @auther Jackie, Mohamad
   */
  public void addResultToPractice() {
    ResultController resultController = new ResultController();

    if (swimEventModels.isEmpty()) {
      VIEW.printWarning("No swim events available.");
    } else {
      viewTablePractice();
      VIEW.printInline("Practice ID: ");
      SwimEventModel practice = InputController.validateSwimEvent(swimEventModels);

      if (practice != null && practice.isPractice()) {
        do {
          MEMBER_CONTROLLER.viewTableMembers();
          VIEW.printInline("Member ID: ");

          try {
            MemberModel member =
                MEMBER_CONTROLLER.getMemberById(
                    InputController.validateMemberId(MEMBER_CONTROLLER.getMembers()));
            do {
              addResultToSwimEvent(practice, resultController.addResultTime(member, practice));
              VIEW.printInline("Add another result to this member [Y/n]: ");
            } while (InputController.promptYesNo());
            VIEW.printInline("Add results for another member, on this practice [Y/n]: ");
          } catch (MemberNotFoundException e) {
            VIEW.printWarning(e.getMessage());
          }

        } while (InputController.promptYesNo());
      } else {
        VIEW.printWarning("Not a valid choice.");
      }
    }
  }

  /**
   * View competition results based on an ID of a swim event.
   *
   * @auther Jackie, Mohamad
   */
  public void viewCompetitionResults() {
    if (swimEventModels.isEmpty()) {
      VIEW.print("No swim events available.");
    } else {
      viewTableCompetitions();
      VIEW.printInline("Competition ID: ");
      SwimEventModel competition =
          InputController.validateSwimEvent(
              swimEventModels); // TODO: refactor to have validation of competition closer

      if (competition != null && !competition.isPractice()) {
        ArrayList<ResultModel> resultsOfCompetition = competition.getResult();

        VIEW.displayCompetitionResults(arrayWithResultToDisplay(resultsOfCompetition));
      } else {
        VIEW.printWarning("Not a valid choice.");
      }
    }
  }

  /**
   * View practice results based on an ID of a swim event.
   *
   * @auther Jackie, Mohamad
   */
  public void viewPracticeResults() {
    if (swimEventModels.isEmpty()) {
      VIEW.print("No swim events available.");
    } else {
      viewTablePractice();
      VIEW.printInline("Practice ID: ");
      SwimEventModel practice =
          InputController.validateSwimEvent(
              swimEventModels); // TODO: refactor to have validation of competition closer
      if (practice != null && !practice.isPractice()) {
        ArrayList<ResultModel> resultsOfCompetition = practice.getResult();

        VIEW.displayCompetitionResults(arrayWithResultToDisplay(resultsOfCompetition));
      } else {
        VIEW.printWarning("Not a valid choice.");
      }
    }
  }

  /**
   * Returns a 2D array of results to be displayed.
   *
   * @param resultTimes Arraylist of result times, that needs to be converted to a string
   * @return A 2d String array
   */
  private String[][] arrayWithResultToDisplay(ArrayList<ResultModel> resultTimes) {
    String[][] result = new String[resultTimes.size()][4]; // TODO: refactor to new table display

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
   * Add the result to a given swim events.
   *
   * @param swimEventModel SwimEventModel
   * @param resultModel ResultModel
   * @auther Mohamad
   */
  private void addResultToSwimEvent(SwimEventModel swimEventModel, ResultModel resultModel) {
    swimEventModel.addResult(resultModel);
    saveSwimEvents();
  }

  /**
   * Saves the swim events to bin.
   *
   * @auther Mohamad
   */
  private void saveSwimEvents() {
    try {
      swimEventService.save(swimEventModels.toArray(new SwimEventModel[0]));
    } catch (IOException e) {
      VIEW.printWarning(e.getMessage());
    }
  }

  /**
   * Converts an array to an arraylist.
   *
   * @param swimEventModels SwimEventModels
   * @return ArrayList<SwimEventModel>
   * @auther Andreas, Mohamad
   */
  private ArrayList<SwimEventModel> toArraylist(SwimEventModel[] swimEventModels) {
    ArrayList<SwimEventModel> result = new ArrayList<>();
    Collections.addAll(result, swimEventModels);

    return result;
  }

  /**
   * Generates the available ID.
   *
   * @return String
   * @auther Mohamad, Mathias
   */
  private String generateSwimEventId() {
    int oldId = 0;
    if (!swimEventModels.isEmpty()) {
      oldId = Integer.parseInt(swimEventModels.get(swimEventModels.size() - 1).getId());
    }
    int newId = oldId + 1;

    return String.valueOf(newId);
  }

  /**
   * Returns a header for table view.
   *
   * @return String[]
   * @auther Mohamad
   */
  private String[] getSwimEventHeader() {
    return new String[] {"ID", "Name", "Date", "Start time"};
  } // TODO: Refactor to new table display

  /** @return a int array with the columwidth */
  public int[] getColumnWidth() { // TODO: Refactor to new table display
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
   * Converts a swim event into a String array.
   *
   * @param swimEventModel that needs to be converted to a String Array
   * @return the the swim event as a String array
   */
  private String[] getSwimEventLine(
      SwimEventModel swimEventModel) { // TODO: Refactor to new table display
    return new String[] {
      swimEventModel.getId(),
      swimEventModel.getName(),
      String.valueOf(swimEventModel.getStartDate()),
      String.valueOf(swimEventModel.getStartTime())
    };
  }

  /**
   * Creates a view for the user of the competitions available.
   *
   * @auther Mohamad
   */
  private void viewTableCompetitions() {
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

  /**
   * Creates a view for the user of the practice available.
   *
   * @auther Jackie
   */
  private void viewTablePractice() {
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

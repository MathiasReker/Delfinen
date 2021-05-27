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

    VIEW.displayOptions(ageGroupToArray());
    int index = InputController.validateOptionRange(AgeGroupType.values().length) - 1;
    AgeGroupType ageGroup = AgeGroupType.values()[index];

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

    VIEW.displayOptions(ageGroupToArray());
    int index = InputController.validateOptionRange(AgeGroupType.values().length) - 1;
    AgeGroupType ageGroup = AgeGroupType.values()[index];

    String name = ageGroup.name() + " practice " + date + " " + startTime;

    swimEventModels.add(
        new SwimEventModel(generateSwimEventId(), name, date, startTime, true, ageGroup));

    VIEW.printSuccess("Practice successfully created.");

    saveSwimEvents();
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
        if (!MEMBER_CONTROLLER.getMembers().isEmpty()) {
          do {
            MEMBER_CONTROLLER.viewTableMembers(false);
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
          VIEW.printWarning("No members available.");
        }
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
        if (!MEMBER_CONTROLLER.getMembers().isEmpty()) {
          do {
            MEMBER_CONTROLLER.viewTableMembers(false);
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
          VIEW.printWarning("No members available.");
        }
      } else {
        VIEW.printWarning("Not a valid choice.");
      }
    }
  }

  private boolean isPractice() {
    for (SwimEventModel swimEventModel : swimEventModels) {
      if (swimEventModel.isPractice()) {
        return true;
      }
    }
    return false;
  }

  /**
   * View competition results based on an ID of a swim event.
   *
   * @auther Jackie, Mohamad
   */
  public void viewCompetitionResults() {
    if (!swimEventModels.isEmpty()) {
      if (isPractice()) {
        VIEW.printWarning("No swim events available.");
      } else {
        viewTableCompetitions();
        VIEW.printInline("Competition ID: ");
        SwimEventModel competition = InputController.validateSwimEvent(swimEventModels);

        if (competition != null && !competition.isPractice()) {
          if (!competition.getResult().isEmpty()) {
            VIEW.printTable(
                getResultsHeader(), getCompetitionResultsContent(competition.getResult()));
          } else {
            VIEW.printWarning("No result for the chosen competition.");
          }
        } else {
          VIEW.printWarning("Not a valid choice.");
        }
      }
    } else {
      VIEW.printWarning("No competitions exists");
    }
  }

  private String[] getResultsHeader() {
    return new String[] {"Name", "Style", "Distance", "Completion time"};
  }
  /**
   * View practice results based on an ID of a swim event.
   *
   * @auther Jackie, Mohamad
   */
  public void viewPracticeResults() {
    if (!swimEventModels.isEmpty()) {
      if (!isPractice()) {
        VIEW.printWarning("No swim events available.");
      } else {
        viewTablePractice();
        VIEW.printInline("Practice ID: ");
        SwimEventModel practice = InputController.validateSwimEvent(swimEventModels);
        if (practice != null && practice.isPractice()) {
          if (!practice.getResult().isEmpty()) {
            VIEW.printTable(getResultsHeader(), getCompetitionResultsContent(practice.getResult()));
          } else {
            VIEW.printWarning("No results for the chosen practice.");
          }
        } else {
          VIEW.printWarning("Not a valid choice.");
        }
      }
    } else {
      VIEW.printWarning("No practices exists");
    }
  }

  private ArrayList<ArrayList<String>> getCompetitionResultsContent(
      ArrayList<ResultModel> results) {
    ArrayList<ArrayList<String>> output = new ArrayList<>();

    for (ResultModel result : results) {
      output.add(getCompetitionsResultsRow(result));
    }

    return output;
  }

  private ArrayList<String> getCompetitionsResultsRow(ResultModel resultModel) {
    ArrayList<String> result = new ArrayList<>();

    result.add(resultModel.getMember().getName());
    result.add(resultModel.getDiscipline().getStyle().name());
    result.add(Integer.toString(resultModel.getDiscipline().getDistance().getMeters()));
    result.add(resultModel.getResultTime().toString());

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
      VIEW.printTable(getSwimEventHeader(), getSwimEventContent(swimEventModels, true));
    }
  }

  /**
   * @param swimEvent SwimEventModel
   * @return ArrayList<String>
   * @auther Mathias
   */
  private ArrayList<String> getSwimEventRows(SwimEventModel swimEvent) {
    ArrayList<String> result = new ArrayList<>();

    result.add(swimEvent.getId());
    result.add(swimEvent.getName());
    result.add(String.valueOf(swimEvent.getStartDate()));
    result.add(String.valueOf(swimEvent.getStartTime()));

    return result;
  }

  /**
   * @param swimEvents ArrayList<SwimEventModel>
   * @param isPractice boolean
   * @return ArrayList<ArrayList<String>>
   * @auther Mathias
   */
  private ArrayList<ArrayList<String>> getSwimEventContent(
      ArrayList<SwimEventModel> swimEvents, boolean isPractice) {
    ArrayList<ArrayList<String>> result = new ArrayList<>();

    for (SwimEventModel swimEvent : swimEvents) {
      if (isPractice != swimEvent.isPractice()) {
        result.add(getSwimEventRows(swimEvent));
      }
    }

    return result;
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
      VIEW.printTable(getSwimEventHeader(), getSwimEventContent(swimEventModels, false));
    }
  }

  /**
   * Convert AgeGroupType enum to String array.
   *
   * @return String[]
   * @auther Mathias
   */
  private String[] ageGroupToArray() {
    String[] result = new String[AgeGroupType.values().length];

    for (int i = 0; i < result.length; i++) {
      result[i] = AgeGroupType.values()[i].name();
    }

    return result;
  }
}

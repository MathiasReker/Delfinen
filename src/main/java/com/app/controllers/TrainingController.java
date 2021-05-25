package com.app.controllers;

import com.app.models.*;
import com.app.models.exceptions.MemberNotFoundException;
import com.app.models.services.ConfigService;
import com.app.models.services.TrainingService;
import com.app.models.types.DistanceType;
import com.app.models.types.GenderType;
import com.app.models.types.StyleType;
import com.app.views.TrainingView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class TrainingController {

  private final TrainingView VIEW = new TrainingView();
  private ArrayList<TrainingModel> trainings;
  private TrainingService trainingService;

  public TrainingController() {
    try {
      trainingService = new TrainingService(new ConfigService("trainingsBin").getPath());
      trainings = toArraylist(trainingService.getTrainingsFromFile());
    } catch (IOException | ClassNotFoundException e) {
      VIEW.printWarning("Could not load any trainings.");
      trainings = new ArrayList<>();
    }
  }

  public void createNewTraining() {
    VIEW.printInline("Date [dd/MM/yyyy]: ");
    LocalDate date =
        LocalDate.parse(InputController.validateDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    trainings.add(new TrainingModel(generateId(), date));

    VIEW.printSuccess("Training successfully created.");

    saveCompetitions();
  }

  public void addResultToTraining() {
    VIEW.printInline("Training ID: ");
    TrainingModel training = InputController.validateTrainingId(trainings);

    VIEW.printInline("Member ID: ");
    MemberModel member =
        getMember(InputController.validateMemberId(new MemberController().getMembers()));
    do {
      addResultTime(member, training);
      VIEW.printInline("Do you wish to add another result to this member [Y/n]: ");
    } while (InputController.promptYesNo());
  }

  public void addResultTime(MemberModel member, TrainingModel training) {
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

    DisciplineModel disciplineModel =
        new DisciplineModel(
            DistanceType.values()[distanceChoice - 1], StyleType.values()[styleChoice - 1]);

    addResultToTraining(training, new ResultModel(member, time, disciplineModel, training));
    VIEW.printSuccess("Result successfully added.");
  }

  public void viewTrainingResults() {
    if (trainings.isEmpty()) {
      VIEW.print("No trainings available.");
    } else {
      VIEW.printInline("Training ID: ");
      TrainingModel training = InputController.validateTrainingId(trainings);

      ArrayList<ResultModel> resultsOfCompetition = training.getResult();

      VIEW.displayTrainings(arrayWithResultToDisplay(resultsOfCompetition));
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

  public void saveCompetitions() {
    try {
      trainingService.saveTrainingsToFile(trainings.toArray(new TrainingModel[0])); //Make a Training service
    } catch (IOException e) {
      VIEW.printWarning(e.getMessage());
    }
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

  public void addResultToTraining(TrainingModel training, ResultModel resultModel) {
    training.addResult(resultModel);
    saveCompetitions();
  }

  private ArrayList<TrainingModel> toArraylist(TrainingModel[] trainings) {
    ArrayList<TrainingModel> result = new ArrayList<>();
    Collections.addAll(result, trainings);

    return result;
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

  private String generateId() {
    int oldId = 0;
    if (!trainings.isEmpty()) {
      oldId = Integer.parseInt(trainings.get(trainings.size() - 1).getId());
    }
    int newId = oldId + 1;

    return String.valueOf(newId);
  }

}

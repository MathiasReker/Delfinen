package com.app.controllers;

import com.app.models.TrainingModel;
import com.app.models.services.ConfigService;
import com.app.models.services.TrainingService;
import com.app.views.TrainingView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class TrainingController {

  private final TrainingView VIEW = new TrainingView();
  private ArrayList<TrainingModel> training;
  private TrainingService trainingService;

  public TrainingController() {
    try {
      trainingService = new TrainingService(new ConfigService("trainingsBin").getPath());
      training = toArraylist(trainingService.getTrainingsFromFile());
    } catch (IOException | ClassNotFoundException e) {
      VIEW.printWarning("Could not load any trainings.");
      training = new ArrayList<>();
    }
  }

  public void createNewTraining() {
    VIEW.printInline("Date [dd/MM/yyyy]: ");
    LocalDate date =
        LocalDate.parse(InputController.validateDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    training.add(new TrainingModel(generateId(), date));

    VIEW.printSuccess("Training successfully created.");

    saveCompetitions();
  }

  public void saveCompetitions() {
    try {
      trainingService.saveTrainingsToFile(training.toArray(new TrainingModel[0])); //Make a Training service
    } catch (IOException e) {
      VIEW.printWarning(e.getMessage());
    }
  }

  private ArrayList<TrainingModel> toArraylist(TrainingModel[] trainings) {
    ArrayList<TrainingModel> result = new ArrayList<>();
    Collections.addAll(result, trainings);

    return result;
  }

  private String generateId() {
    int oldId = 0;
    if (!training.isEmpty()) {
      oldId = Integer.parseInt(training.get(training.size() - 1).getId());
    }
    int newId = oldId + 1;

    return String.valueOf(newId);
  }

}

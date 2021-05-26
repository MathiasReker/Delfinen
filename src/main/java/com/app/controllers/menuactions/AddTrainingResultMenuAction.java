package com.app.controllers.menuactions;

import com.app.controllers.TrainingController;

public class AddTrainingResultMenuAction extends MenuAction {

  public AddTrainingResultMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    new TrainingController().addResultToTraining();
  }
}

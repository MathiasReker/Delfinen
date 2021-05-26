package com.app.controllers.menuactions;

import com.app.controllers.TrainingController;

public class CreateTrainingMenuMenuAction extends MenuAction {
  public CreateTrainingMenuMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    new TrainingController().createNewTraining();
  }
}

package com.app.controllers.menuactions;

import com.app.controllers.TrainingController;

public class ViewTrainingResultsMenuAction extends MenuAction{
  public ViewTrainingResultsMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    new TrainingController().viewTrainingResults();
  }
}

package com.app.controllers.menuactions;

import com.app.controllers.MenuController;

public class TrainingMenuAction extends MenuAction {
  public TrainingMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    new MenuController("Training Management", "Please choose an option: ", menu()).run();
  }

  MenuAction[] menu() {
    return new MenuAction[]{
        new CreateTrainingMenuMenuAction("Create Training"),
        new AddTrainingResultMenuAction("Add Training Results"),
        new ViewTrainingResultsMenuAction("View Training Results"),
        new ExitMenuAction("Back")
    };
  }

}

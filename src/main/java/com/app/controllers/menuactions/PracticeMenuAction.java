package com.app.controllers.menuactions;

import com.app.controllers.MenuController;

public class PracticeMenuAction extends MenuAction {
  public PracticeMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    new MenuController("Practice Management", "Please choose an option: ", menu()).run();
  }

  MenuAction[] menu() {
    return new MenuAction[] {
      new CreateTrainingMenuMenuAction("Create Practice"),
      new AddTrainingResultMenuAction("Add Results"),
      new ViewTrainingResultsMenuAction("View Results"),
      new ExitMenuAction("Back")
    };
  }
}

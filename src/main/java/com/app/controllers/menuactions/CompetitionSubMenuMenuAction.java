package com.app.controllers.menuactions;

import com.app.controllers.MenuController;

public class CompetitionSubMenuMenuAction extends MenuAction {
  public CompetitionSubMenuMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    new MenuController("Competition Management", "Please choose an option: ", menu()).run();
  }

  MenuAction[] menu() {
    return new MenuAction[] {
      new CreateCompetitionSubMenuMenuAction("Create Competition"),
      new AddResultMenuAction("Add Result"),
      new ViewResultsMenuAction("View Results"),
      new TrainingMenuAction("Training Management"),
      new ExitMenuAction("Back")
    };
  }
}

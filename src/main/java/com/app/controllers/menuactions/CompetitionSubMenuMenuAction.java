package com.app.controllers.menuactions;

import com.app.controllers.MenuController;

public class CompetitionSubMenuMenuAction extends MenuActions {
  public CompetitionSubMenuMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    new MenuController("Competition Management", "Please choose an option: ", menu()).run();
  }

  MenuActions[] menu() {
    return new MenuActions[] {
      new CreateCompetitionSubMenuMenuAction("Create Competition"),
      new AddResultMenuAction("Add Result"),
      new ViewResultsMenuAction("View Results"),
      new ExitMenuAction("Back")
    };
  }
}

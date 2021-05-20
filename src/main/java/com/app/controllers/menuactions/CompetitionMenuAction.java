package com.app.controllers.menuactions;

import com.app.controllers.MenuController;

public class CompetitionMenuAction extends MenuActions {
  public CompetitionMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    new MenuController("Competition Management", "Please choose an option: ", menu()).run();
  }

  MenuActions[] menu() {
    return new MenuActions[] {
        new CreateCompetitionSubMenuAction("Create Competition"),
        new AddResultSubMenuAction("Add Result"),
        new ViewResultsSubMenuAction("View Results"),
        new ExitMenuAction("Back")
    };
  }
}

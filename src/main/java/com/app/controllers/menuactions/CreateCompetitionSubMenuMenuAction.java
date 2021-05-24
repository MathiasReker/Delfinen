package com.app.controllers.menuactions;

import com.app.controllers.CompetitionController;

public class CreateCompetitionSubMenuMenuAction extends MenuAction {
  public CreateCompetitionSubMenuMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    CompetitionController competitionController = new CompetitionController();
    competitionController.createNewCompetition();
  }
}

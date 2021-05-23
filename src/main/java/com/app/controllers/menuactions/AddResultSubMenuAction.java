package com.app.controllers.menuactions;

import com.app.controllers.CompetitionController;

public class AddResultSubMenuAction extends MenuActions {

  public AddResultSubMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    new CompetitionController().addResultToCompetition();
  }
}

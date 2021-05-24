package com.app.controllers.menuactions;

import com.app.controllers.CompetitionController;

public class ViewResultsSubMenuAction extends MenuActions {

  public ViewResultsSubMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    new CompetitionController().viewCompetitionResults();
  }
}

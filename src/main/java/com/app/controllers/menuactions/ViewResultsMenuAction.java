package com.app.controllers.menuactions;

import com.app.controllers.CompetitionController;

public class ViewResultsMenuAction extends MenuAction {

  public ViewResultsMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    new CompetitionController().viewCompetitionResults();
  }
}

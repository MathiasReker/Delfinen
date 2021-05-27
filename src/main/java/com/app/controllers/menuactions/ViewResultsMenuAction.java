package com.app.controllers.menuactions;

import com.app.controllers.SwimEventController;

public class ViewResultsMenuAction extends MenuAction {

  public ViewResultsMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    new SwimEventController().viewCompetitionResults();
  }
}

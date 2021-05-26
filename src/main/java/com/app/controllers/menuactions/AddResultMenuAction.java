package com.app.controllers.menuactions;

import com.app.controllers.SwimEventController;

public class AddResultMenuAction extends MenuAction {

  public AddResultMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    new SwimEventController().addResultToCompetition();
  }
}

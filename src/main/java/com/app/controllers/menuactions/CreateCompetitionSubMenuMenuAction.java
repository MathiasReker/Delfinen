package com.app.controllers.menuactions;

import com.app.controllers.SwimEventController;

public class CreateCompetitionSubMenuMenuAction extends MenuAction {
  public CreateCompetitionSubMenuMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    SwimEventController swimEventController = new SwimEventController();
    swimEventController.createNewCompetition();
  }
}

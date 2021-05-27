package com.app.controllers.menuactions;

import com.app.controllers.SwimEventController;

public class ViewTrainingResultsMenuAction extends MenuAction {
  public ViewTrainingResultsMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    new SwimEventController().viewPracticeResults();
  }
}

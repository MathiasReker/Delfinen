package com.app.controllers.menuactions;

import com.app.controllers.LeaderboardController;

public class TopResultsMenuAction extends MenuAction {

  public TopResultsMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    new LeaderboardController().displayTop5Results();
  }
}

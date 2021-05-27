package com.app.controllers.menuactions;

import com.app.controllers.LeaderboardController;

public class Top5ResultsMenuAction extends MenuAction {

  public Top5ResultsMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    new LeaderboardController().displayTopResults(5);
  }
}

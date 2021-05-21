package com.app.controllers.menuactions;

import com.app.controllers.LeaderboardController;

import java.util.Scanner;

public class TopResultsMenuAction extends MenuActions {

  public TopResultsMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    Scanner in = new Scanner(System.in);
    new LeaderboardController().displayTopResults(in);
  }
}

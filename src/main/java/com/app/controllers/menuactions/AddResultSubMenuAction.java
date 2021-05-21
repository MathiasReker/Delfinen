package com.app.controllers.menuactions;

import com.app.controllers.CompetitionController;

import java.util.Scanner;

public class AddResultSubMenuAction extends MenuActions {

  public AddResultSubMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    CompetitionController competitionController = new CompetitionController();
    Scanner in = new Scanner(System.in);
    competitionController.addResultToCompetition(in);
  }
}

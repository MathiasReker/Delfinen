package com.app.controllers.menuactions;

import com.app.controllers.CompetitionController;

import java.util.Scanner;

public class CreateCompetitionSubMenuAction extends MenuActions {

  public CreateCompetitionSubMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    CompetitionController competitionController = new CompetitionController();
    Scanner in = new Scanner(System.in);
    competitionController.createNewCompetition(in);
  }
}

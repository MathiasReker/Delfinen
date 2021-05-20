package com.app.controllers.menuactions;

import com.app.controllers.CompetitionController;

import java.util.Scanner;

public class ViewResultsSubMenuAction extends MenuActions{

  public ViewResultsSubMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    Scanner in = new Scanner(System.in);
    new CompetitionController().viewCompetitionResults(in);
  }
}

package com.app.controllers.menuactions;

import com.app.controllers.CompetitionController;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class AddResultSubMenuAction extends MenuActions {


  public AddResultSubMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    Scanner in = new Scanner(System.in);
    new CompetitionController().createResultToCompetition(in);
  }
}

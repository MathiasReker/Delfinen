package com.app.controllers.menuactions;

import com.app.controllers.CompetitionController;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class CreateCompetitionSubMenuAction extends MenuActions{

  public CreateCompetitionSubMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run(){
    Scanner in = new Scanner(System.in);
    new CompetitionController().createNewCompetition(in);
  }
}

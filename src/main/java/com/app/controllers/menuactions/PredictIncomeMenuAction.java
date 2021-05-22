package com.app.controllers.menuactions;

import com.app.controllers.PredictionController;

import java.util.Scanner;

public class PredictIncomeMenuAction extends MenuActions {

  public PredictIncomeMenuAction(String description) {
    super(description);
  }

  public void run() {
    Scanner in = new Scanner(System.in);
    new PredictionController().predictIncome(in);
  }
}

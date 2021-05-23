package com.app.controllers.menuactions;

import com.app.controllers.PredictionController;

import java.util.Scanner;

public class PredictIncomeMenuAction extends MenuActions {

  public PredictIncomeMenuAction(String description) {
    super(description);
  }

  public void run() {
    new PredictionController().predictIncome();
  }
}

package com.app.controllers.menuactions;

import com.app.controllers.PredictionController;

public class PredictIncomeMenuAction extends MenuAction {

  public PredictIncomeMenuAction(String description) {
    super(description);
  }

  @Override
  public void run() {
    new PredictionController().predictIncome();
  }
}

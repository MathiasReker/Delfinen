package com.app.controllers.menuactions;

import com.app.controllers.PredictionController;

public class PredictIncomeDefinedMenuAction extends MenuAction {

  public PredictIncomeDefinedMenuAction(String description) {
    super(description);
  }

  @Override
  public void run() {
    new PredictionController().predictIncome(30);
  }
}

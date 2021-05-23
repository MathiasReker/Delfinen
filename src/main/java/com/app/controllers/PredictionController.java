package com.app.controllers;

import com.app.controllers.utils.Input;
import com.app.models.MemberModel;
import com.app.models.PricingModel;
import com.app.views.PredictionView;

import java.util.ArrayList;
import java.util.Scanner;

public class PredictionController {
  private final PredictionView VIEW = new PredictionView();

  public PredictionController() {}

  public void predictIncome() {
    VIEW.print("The prediction is calculated based on how many members will expire.");
    VIEW.printInline("Input the amount of days to predict ahead: ");
    int days = Input.validateInteger();

    int prediction = predictIncomeInXDays(days);

    VIEW.printExpectedIncome(prediction, days);
  }

  public void predictIncome(int days) {
    int prediction = predictIncomeInXDays(days);

    VIEW.printExpectedIncome(prediction, days);
  }

  public int predictIncomeInXDays(int days) {
    ArrayList<MemberModel> expiringMembers =
        new MemberModel()
            .getExpiringMembers(
                new MemberController().getMembers().toArray(new MemberModel[0]), days);

    int result = 0;
    for (MemberModel member : expiringMembers) {
      result += PricingModel.calculateMemberPrice(member);
    }

    return result;
  }
}

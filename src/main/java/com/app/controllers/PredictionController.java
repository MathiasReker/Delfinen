package com.app.controllers;

import com.app.models.MemberModel;
import com.app.models.PricingModel;
import com.app.views.PredictionView;

import java.util.ArrayList;

public class PredictionController {
  private final PredictionView VIEW = new PredictionView();
  private final MemberController MEMBER_CONTROLLER = new MemberController();

  public PredictionController() {}

  public void predictIncome() {
    VIEW.print("The prediction is calculated based on how many members will expire.");
    VIEW.printInline("Input the amount of days to predict ahead: ");
    int days = InputController.validateInteger();

    int prediction = predictIncomeInXDays(days);

    VIEW.printExpectedIncome(prediction, days);
  }

  public void predictIncome(int days) {
    int prediction = predictIncomeInXDays(days);

    VIEW.printExpectedIncome(prediction, days);
  }

  public int predictIncomeInXDays(int days) {
    ArrayList<MemberModel> expiringMembers =
        MEMBER_CONTROLLER.getExpiringMembers(
            MEMBER_CONTROLLER.getMembers().toArray(new MemberModel[0]), days);

    int result = 0;
    for (MemberModel member : expiringMembers) {
      result += PricingModel.getMemberPrice(member);
    }

    return result;
  }
}

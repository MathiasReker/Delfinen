package com.app.controllers;

import com.app.models.MemberModel;
import com.app.models.PricingModel;
import com.app.views.PredictionView;

import java.util.ArrayList;

public class PredictionController {
  private final PredictionView VIEW = new PredictionView();
  private final MemberController MEMBER_CONTROLLER = new MemberController();

  /**
   * Starts prediction flow with user input.
   *
   * @auther Andreas
   */
  public void predictIncome() {
    if (!MEMBER_CONTROLLER.getMembers().isEmpty()) {
      VIEW.print("The prediction is calculated based on how many members will expire.");
      VIEW.printInline("Input the amount of days to predict ahead: ");
      int days = InputController.validateInteger();

      int prediction = predictIncomeInAmountOfDays(days);

      VIEW.printExpectedIncome(prediction, days);
    } else {
      VIEW.printWarning("No members exists");
    }
  }

  /**
   * Display predicted income by n days.
   *
   * @param days int
   * @auther Andreas
   */
  public void predictIncome(int days) {
    if (!MEMBER_CONTROLLER.getMembers().isEmpty()) {
      VIEW.print("The prediction is calculated based on how many members will expire.");
      int prediction = predictIncomeInAmountOfDays(days);

      VIEW.printExpectedIncome(prediction, days);
    } else {
      VIEW.printWarning("No members exists");
    }
  }

  /**
   * Returns predicted income in n days.
   *
   * @param days int
   * @return int
   * @auther Andreas
   */
  private int predictIncomeInAmountOfDays(int days) {
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

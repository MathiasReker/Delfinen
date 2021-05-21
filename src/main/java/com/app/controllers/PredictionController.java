package com.app.controllers;

import com.app.models.MemberModel;
import com.app.models.PricingModel;
import com.app.views.PredictionView;

import java.util.ArrayList;
import java.util.Scanner;

public class PredictionController {
  private final MemberController MEMBER_CONTROLLER = new MemberController();
  private final PredictionView VIEW = new PredictionView();

  public PredictionController() {}

  public void predictIncome(Scanner in){
    VIEW.print("Data is based om how many Members that expire");
    VIEW.printInline("Input amount of days to predict ahead:");
    int days = in.nextInt();

    int prediction = predictIncomeInXDays(days);

    VIEW.printExpectedIncome(prediction,days);
  }

  public void predictIncome(int days){
    int prediction = predictIncomeInXDays(days);

    VIEW.printExpectedIncome(prediction,days);

  }


  public int predictIncomeInXDays(int days) {
    int result = 0;
    ArrayList<MemberModel> expiringMembers =
        MEMBER_CONTROLLER.getExpiringMembers(MEMBER_CONTROLLER.getMEMBERS().toArray(new MemberModel[0]), days);

    for (MemberModel member : expiringMembers) {
      result += PricingModel.calculateMemberPrice(member);
    }

    return result;
  }
}

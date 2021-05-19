package com.app.controllers;

import com.app.models.MemberModel;
import com.app.models.PricingModel;
import com.app.views.GenericView;

import java.util.ArrayList;
import java.util.Scanner;

public class PredictionController {
  private final MemberController MEMBER_CONTROLLER = new MemberController();
  private final GenericView VIEW = new GenericView();

  public PredictionController() {}

  public void predictIncome(Scanner in){
    VIEW.print("Data is based om how many Members that expire");
    VIEW.printInline("Input amount of days to predict ahead:");
    int days = in.nextInt();

    int prediction = predictIncomeInXDays(days);

    VIEW.print(prediction + " øre");
  }

  public int predictIncomeInXDays(int days) {
    int result = 0;
    ArrayList<MemberModel> expiringMembers =
        MEMBER_CONTROLLER.getExpiringMembers(MEMBER_CONTROLLER.getMEMBERS(), days);

    for (MemberModel member : expiringMembers) {
      result += PricingModel.calculateMemberPrice(member);
    }

    return result;
  }
}

package com.app.controllers;

import com.app.models.MemberModel;
import com.app.models.PricingModel;

import java.util.ArrayList;

public class PredictionController {
  private final MemberController MEMBER_CONTROLLER = new MemberController();

  public PredictionController() {}

  public int predictIncomeInXDays(int days) {
    int result = 0;
    ArrayList<MemberModel> expiringMembers =
        MEMBER_CONTROLLER.getExpiringMembers(MEMBER_CONTROLLER.getNenbers(), days);

    for(MemberModel member : expiringMembers){
      result += PricingModel.calculateMemberPrice(member);
    }
    
    return result;
  }
}

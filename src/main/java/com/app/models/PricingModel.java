package com.app.models;

import com.app.models.types.AgeGroupType;

public class PricingModel {
  private static final int PASSIVE_PRICE = 50000;
  private static final int JUNIOR_PRICE = 100000;
  private static final int SENIOR_PRICE = 160000;

  /**
   * Returns hardcoded price based on requirements
   *
   * @param member member to get price of.
   * @return price
   */
  public static int calculateMemberPrice(MemberModel member) {
    if(!member.getLatestMembership().isActive()){
      return PASSIVE_PRICE; //Passive price
    }
    if (member.getAgeGroup().equals(AgeGroupType.JUNIOR.name())) {
      return JUNIOR_PRICE; // Junior price
    } else {
      if (member.getAge() > 60) {
        return (int) (SENIOR_PRICE * 0.75); // 25 % discount
      }
      return SENIOR_PRICE; // Senior price
    }
  }
  public static int calculateDifference(){

  }
}

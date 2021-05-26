package com.app.models;

import com.app.models.types.AgeGroupType;

public class PricingModel {
  private static final int JUNIOR_PRICE = 1000; // price in DKK
  private static final int SENIOR_PRICE = 1600; // price in DKK
  private static final int DISCOUNT_PERCENT = 25; // discount in percent

  /**
   * Returns hardcoded price based on requirements
   *
   * @param member member to get price of.
   * @return price
   */
  public static int calculateMemberPrice(MemberModel member) {
    if (member.getAgeGroup().equals(AgeGroupType.JUNIOR)) {
      return JUNIOR_PRICE * 100; // price in øre
    } else {
      if (member.getAge() > 60) {
        return (int) (SENIOR_PRICE * (100 - DISCOUNT_PERCENT) / 100.0);
      }

      return SENIOR_PRICE * 100; // price in øre
    }
  }
}

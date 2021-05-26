package com.app.models;

import com.app.models.types.AgeGroupType;

public class PricingModel {
  private static final int DISCOUNT_PERCENT = 25; // discount in percent

  /**
   * Returns hardcoded price based on requirements
   *
   * @param member member to get price of.
   * @return price
   */
  public static int calculateMemberPrice(MemberModel member) {
    if (member.getAgeGroup().equals(AgeGroupType.JUNIOR)) {
      return AgeGroupType.JUNIOR.getPrice();
    } else {
      if (member.getAge() > 60) {
        return (int) (AgeGroupType.SENIOR.getPrice() * (100 - DISCOUNT_PERCENT) / 100.0);
      }

      return AgeGroupType.SENIOR.getPrice();
    }
  }
}

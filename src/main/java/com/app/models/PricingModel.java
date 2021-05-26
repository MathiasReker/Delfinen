package com.app.models;

import com.app.models.types.AgeGroupType;

public class PricingModel {
  /**
   * Returns hardcoded price based on requirements
   *
   * @param member member to get price of.
   * @return price
   */
  public static int getMemberPrice(MemberModel member) {
    if (member.getAgeGroup().equals(AgeGroupType.JUNIOR)) {
      return AgeGroupType.JUNIOR.getPrice();
    } else {
      if (member.getAge() > 60) {
        return getPriceWithDiscount(AgeGroupType.SENIOR.getPrice(), 25);
      }

      return AgeGroupType.SENIOR.getPrice();
    }
  }

  private static int getPriceWithDiscount(int price, int discountPercent) {
    return (int) (price * (100 - discountPercent) / 100.0);
  }
}

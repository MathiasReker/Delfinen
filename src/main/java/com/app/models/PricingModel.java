package com.app.models;

import com.app.models.types.AgeGroupType;

public class PricingModel {
  private static final int PASSIVE_PRICE = 500;
  private static final int JUNIOR_PRICE = 1000;
  private static final int SENIOR_PRICE = 1600;

  /**
   * Returns hardcoded price based on requirements
   *
   * @param member member to get price of.
   * @return price
   */
  public static int getMemberPrice(MemberModel member) {
    if (!member.getLatestMembership().isActive()) {
      return getPriceInOre(PASSIVE_PRICE);
    }

    if (member.getAgeGroup().equals(AgeGroupType.JUNIOR)) {
      return getPriceInOre(JUNIOR_PRICE);
    } else {
      if (member.getAge() > 60) {
        int priceWithDiscount = getPriceWithDiscount(AgeGroupType.SENIOR.getPrice(), 25);
        return getPriceInOre(priceWithDiscount);
      }

      return getPriceInOre(SENIOR_PRICE);
    }
  }

  private static int getPriceWithDiscount(int price, int discountPercent) {
    return (int) (price * (100 - discountPercent) / 100.0);
  }

  private static int getPriceInOre(int price) {
    return price * 100;
  }
}

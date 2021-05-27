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
   * @auther Andreas, Mathias
   */
  public static int getMemberPrice(MemberModel member) {
    if (!member.getLatestMembership().isActive()) {
      return getPriceInOre(PASSIVE_PRICE);
    }

    if (member.getAgeGroup().equals(AgeGroupType.JUNIOR)) {
      return getPriceInOre(JUNIOR_PRICE);
    } else {
      if (member.getAge() > 60) {
        int priceWithDiscount = getPriceWithDiscount(SENIOR_PRICE, 25);
        return getPriceInOre(priceWithDiscount);
      }

      return getPriceInOre(SENIOR_PRICE);
    }
  }

  /**
   * Returns price with discount.
   *
   * @param price int
   * @param discountPercent int
   * @return int
   * @auther Mathias
   */
  private static int getPriceWithDiscount(int price, int discountPercent) {
    return (int) (price * (100 - discountPercent) / 100.0);
  }

  /**
   * Returns price in ore.
   *
   * @param price int
   * @return int
   * @auther Mathias
   */
  private static int getPriceInOre(int price) {
    return price * 100;
  }
}

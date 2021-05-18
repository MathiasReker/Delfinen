package com.app.models;

import java.time.LocalDate;

public class PricingModel {

  /**
   * Returns hardcoded price based on requirements
   *
   * @param member member to get price of.
   * @return
   */
  public static int calculateMemberPrice(MemberModel member) {
    int age = member.getBirthdate().getYear() - LocalDate.now().getYear();
    if (age < 18) {
      return 100000;
    }
    if (age > 18 && age < 60) {
      return 160000;
    } else {
      double price = 160000 * 0.8;
      return (int) price;
    }
  }
}

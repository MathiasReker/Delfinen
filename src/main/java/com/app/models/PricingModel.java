package com.app.models;

public class PricingModel {

  /**
   * Returns hardcoded price based on requirements
   *
   * @param member member to get price of.
   * @return price
   */
  public static int calculateMemberPrice(MemberModel member) {
    int age = member.getAge();
    if (age < 18) {
      return 100000; // Youth price
    }
    if (age > 18 && age < 60) {
      return 160000; // Senior price
    } else {
      return (int) (160000 * 0.75); // 25 % discount
    }
  }
}

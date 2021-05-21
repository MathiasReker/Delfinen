package com.app.models;

public class PricingModel {

  /**
   * Returns hardcoded price based on requirements
   *
   * @param member member to get price of.
   * @return
   */
  public static int calculateMemberPrice(MemberModel member) {
    int age = member.getAge();
    if (age < 18) {
      return 100000; // todo: save in variable
    }
    if (age > 18 && age < 60) {
      return 160000; // todo: save in variable
    } else {
      double price = 160000 * 0.8; // todo: save in variable
      return (int) price;
    }
  }
}

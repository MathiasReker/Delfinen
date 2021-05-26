package com.app.models;

import com.app.models.types.AgeGroupType;

public class PricingModel {

  /**
   * Returns hardcoded price based on requirements
   *
   * @param member member to get price of.
   * @return price
   */
  public static int calculateMemberPrice(MemberModel member) {
    if(!member.getLatestMembership().isActive()){
      return 50000;
    }
    if (member.getAgeGroup().equals(AgeGroupType.JUNIOR.name())) {
      return 100000; // Junior price
    } else {
      if (member.getAge() > 60) {
        return (int) (160000 * 0.75); // 25 % discount
      }
      return 160000; // Senior price
    }
  }
}

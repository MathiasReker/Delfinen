package com.app.controllers;

import com.app.models.MemberModel;
import com.app.models.MembershipModel;

import java.time.LocalDate;
import java.util.ArrayList;

public class MembershipController {
  /**
   * Add active membership to member.
   *
   * @param member MemberModel
   * @auther Andreas
   */
  void addActiveMembership(MemberModel member) {
    member.addMembership(createNewMembership(member, LocalDate.now(), 1, true));
  }

  /**
   * Add passive membership to member.
   *
   * @param member MemberModel
   * @auther Andreas
   */
  void addPassiveMembership(MemberModel member) {
    member.addMembership((createNewMembership(member, LocalDate.now(), 1, false)));
  }

  /**
   * Generate new membership ID.
   *
   * @param member MemberModel
   * @auther Andreas
   */
  private String generateMembershipId(MemberModel member) {
    ArrayList<MembershipModel> memberships = member.getMemberships();
    int oldId = 0;
    if (!memberships.isEmpty()) {
      oldId = Integer.parseInt(memberships.get(memberships.size() - 1).getID());
    }
    int result = oldId + 1;

    return String.valueOf(result);
  }

  /**
   * Renewing new memberships for the member with duration.
   *
   * @param member Member to renew
   * @param durationYears years to add to membership
   * @auther Andreas
   */
  void renewMembership(MemberModel member, int durationYears) {
    ArrayList<MembershipModel> memberships = member.getMemberships();
    MembershipModel lastMembership = memberships.get(memberships.size() - 1);

    int comparedDate = lastMembership.getExpiringDate().compareTo(LocalDate.now());

    if (comparedDate < 0) {
      MembershipModel newMembership =
          createNewMembership(member, LocalDate.now(), durationYears, lastMembership.isActive());
      member.addMembership(newMembership);
    } else if (comparedDate > 0) {
      MembershipModel newMembership =
          createNewMembership(
              member,
              lastMembership.getExpiringDate().plusDays(1),
              durationYears,
              lastMembership.isActive());
      member.addMembership(newMembership);
    } else {
      MembershipModel newMembership =
          createNewMembership(
              member, LocalDate.now().plusDays(1), durationYears, lastMembership.isActive());
      member.addMembership(newMembership);
    }
  }

  /**
   * Returns a new membership.
   *
   * @param member MemberModel
   * @param date LocalDate
   * @param durationYears int
   * @param active boolean
   * @return MembershipModel
   * @auther Andreas
   */
  private MembershipModel createNewMembership(
      MemberModel member, LocalDate date, int durationYears, boolean active) {
    MembershipModel result = new MembershipModel(generateMembershipId(member));
    result.setStartingDate(date);
    result.setExpiringDate(result.getStartingDate().plusYears(durationYears));
    result.setActive(active);
    result.setPaid(false);

    return result;
  }

  /**
   * Returns true if membership expires in n days. Else returns false.
   *
   * @param member MemberModel
   * @param days int
   * @return boolean
   * @auther Andreas
   */
  boolean membershipExpiresInDays(MemberModel member, int days) {
    return member.getLatestMembership().getExpiringDate().minusDays(days).compareTo(LocalDate.now())
        <= 0;
  }

  /**
   * Returns true if membership is unpaid. Else returns false.
   *
   * @param member MemberModel
   * @return boolean
   * @auther Andreas
   */
  boolean membershipUnpaid(MemberModel member) {
    return member.getLatestMembership().isPaid();
  }
}

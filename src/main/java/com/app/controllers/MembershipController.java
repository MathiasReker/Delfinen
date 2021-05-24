package com.app.controllers;

import com.app.models.MemberModel;
import com.app.models.MembershipModel;

import java.time.LocalDate;
import java.util.ArrayList;

public class MembershipController {

  public void addMembership(MemberModel member) {
    member.addMembership(createNewMembership(member, LocalDate.now(), 1));
  }

  private String generateMembershipId(MemberModel member) {
    ArrayList<MembershipModel> memberships = member.getMemberships();
    int oldId = 0;
    if (memberships.size() > 0) {
      oldId = Integer.parseInt(memberships.get(memberships.size() - 1).getID());
    }
    int result = oldId + 1;

    return String.valueOf(result);
  }

  /**
   * Method for renewing memberships.
   *
   * @param member Member to renew
   * @param durationYears years to add to membership
   */
  public void renewMembership(MemberModel member, int durationYears) {
    ArrayList<MembershipModel> memberships = member.getMemberships();
    MembershipModel lastMembership = memberships.get(memberships.size() - 1);
    int comparedDate = lastMembership.getExpiringDate().compareTo(LocalDate.now());
    if (comparedDate < 0) {
      MembershipModel newMembership = createNewMembership(member, LocalDate.now(), durationYears);
      member.addMembership(newMembership);
    } else if (comparedDate > 0) {
      MembershipModel newMembership =
          createNewMembership(member, lastMembership.getExpiringDate().plusDays(1), durationYears);
      member.addMembership(newMembership);
    } else {
      MembershipModel newMembership =
          createNewMembership(member, LocalDate.now().plusDays(1), durationYears);
      member.addMembership(newMembership);
    }
  }

  private MembershipModel createNewMembership(
      MemberModel member, LocalDate date, int durationYears) {
    MembershipModel result = new MembershipModel(generateMembershipId(member));
    result.setStartingDate(date);
    result.setExpiringDate(result.getExpiringDate().plusYears(durationYears));
    result.setActive(true);
    result.setPayed(false);

    return result;
  }
}

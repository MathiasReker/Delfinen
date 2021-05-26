package com.app.controllers;

import com.app.models.MemberModel;
import com.app.models.MembershipModel;

import java.time.LocalDate;
import java.util.ArrayList;

public class MembershipController {

  public void addActiveMembership(MemberModel member) {
    member.addMembership(createNewMembership(member, LocalDate.now(), 1, true));
  }

  public void addPassiveMembership(MemberModel member) {
    member.addMembership((createNewMembership(member, LocalDate.now(), 1, false)));
  }

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

  private MembershipModel createNewMembership(
      MemberModel member, LocalDate date, int durationYears, boolean active) {
    MembershipModel result = new MembershipModel(generateMembershipId(member));
    result.setStartingDate(date);
    result.setExpiringDate(result.getStartingDate().plusYears(durationYears));
    result.setActive(active);
    result.setPayed(false);

    return result;
  }

  public boolean membershipExpiresInDays(MemberModel member,int days){
    if(member.getLatestMembership().getExpiringDate().minusDays(days).compareTo(LocalDate.now()) <= 0){
      return true;
    }
    return false;
  }

  public boolean membershipUnpaid(MemberModel member){
    if(member.getLatestMembership().isPayed()){
      return true;
    }
    return false;
  }
}

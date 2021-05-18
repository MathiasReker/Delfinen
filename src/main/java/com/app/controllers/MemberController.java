package com.app.controllers;

import com.app.models.MemberModel;
import com.app.models.MembershipModel;
import com.app.models.PricingModel;
import com.app.models.services.PaymentRequestService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MemberController {

  public void renewMembers() {
    MemberModel[] members = createMembersForTest();

    for (MemberModel member : members) {
      ArrayList<MembershipModel> membershipModels = member.getMemberships();
      for (MembershipModel membership : membershipModels) {
        if (membership.isActive() && membership.isPayed()) {
          if (membership.getExpiringDate().compareTo(LocalDate.now()) > 0) {}
        }
      }
    }
  }

  public void renewExpiringMembers() {
    try {
      PaymentRequestService paymentRequester =
          new PaymentRequestService("db/PaymentRequests/pr.txt");
      MemberModel[] experingMembers = getExpiringMembers(createMembersForTest(), 30).toArray(new MemberModel[0]);

      paymentRequester.createPaymentRequest(experingMembers);

    } catch (IOException e) {
      System.out.println("cant do that");
    }


    // show Members

    // allow removal of members

  }

  /**
   * Returns an Arraylist of expiring members based on the Array given as argument
   *
   * @param days Amount of days to look ahead of current day.
   * @param memberModels Array of members to look through
   * @return ArrayList of expiring members
   */
  public ArrayList<MemberModel> getExpiringMembers(
      MemberModel[] memberModels, int days) { // TODO Move to model?
    ArrayList<MemberModel> result = new ArrayList<>();

    for (MemberModel member : memberModels) {
      ArrayList<MembershipModel> memberships = member.getMemberships();
      LocalDate expiringDate = memberships.get(memberships.size() - 1).getExpiringDate();
      if (expiringDate.minusDays(days).compareTo(LocalDate.now()) <= 0) {
        result.add(member);
      }
    }
    return result;
  }

  // Delete when depricated
  private MemberModel[] createMembersForTest() {
    MemberModel[] members = {
      new MemberModel(), new MemberModel(), new MemberModel(), new MemberModel()
    };

    for (MemberModel member : members) {
      int test = 10;
      member.setID("M" + test);
      member.setName("Name" + test);
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
      member.addMembership(new MembershipModel());
      member.getMemberships().get(0).setExpiringDate(LocalDate.parse(test + "-10-2020", formatter));
      member.getMemberships().get(0).setActive(true);
      member.getMemberships().get(0).setPayed(true);
      test++;
    }

    return members;
  }
}

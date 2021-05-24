package com.app.controllers;

import com.app.models.MemberModel;
import com.app.models.MembershipModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

public class PaymentControllerTest {

  @Test
  public void testArreardays() {
    PaymentController paymentController = new PaymentController();
    ArrayList<MemberModel> memberModels = new ArrayList<>();
    MemberModel testMember = new MemberModel();
    MembershipModel testMembership =
        new MembershipModel(
            "test", LocalDate.now().minusDays(14), LocalDate.now().plusYears(1), false, true);

    memberModels.add(testMember);
    testMember.addMembership(testMembership);

    ArrayList<MemberModel> result = paymentController.findMembersInArrear(memberModels);

    Assertions.assertEquals(result.get(0).getId(),memberModels.get(0).getId());

  }
}

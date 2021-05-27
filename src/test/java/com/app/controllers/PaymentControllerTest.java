package com.app.controllers;

import com.app.models.MemberModel;
import com.app.models.MembershipModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

public class PaymentControllerTest {

  @Test
  public void testArrearsFind() {
    PaymentController paymentController = new PaymentController();
    ArrayList<MemberModel> memberModels = new ArrayList<>();
    MemberModel testMember = new MemberModel();
    MembershipModel testMembership =
        new MembershipModel(
            "test", LocalDate.now().minusDays(14), LocalDate.now().plusYears(1), false, true);

    memberModels.add(testMember);
    testMember.addMembership(testMembership);

    ArrayList<MemberModel> result = paymentController.getMembersInArrears(memberModels);

    Assertions.assertEquals(result.get(0).getId(), memberModels.get(0).getId());
  }

  @Test
  public void testArrearsCalcPeriod() {
    PaymentController paymentController = new PaymentController();

    LocalDate test1 = LocalDate.now();
    LocalDate test2 = LocalDate.now().plusDays(14);
    String expected = "14";

    Assertions.assertEquals(expected, paymentController.calcPeriod(test1, test2));
  }

  @Test
  public void testArrearsCalcPeriodYear() {
    PaymentController paymentController = new PaymentController();

    LocalDate test1 = LocalDate.now();
    LocalDate test2 = LocalDate.now().plusDays(365);
    String expected = "365";
    if (LocalDate.now().isLeapYear()) {
      expected = "366";
    }

    Assertions.assertEquals(expected, paymentController.calcPeriod(test1, test2));
  }
}

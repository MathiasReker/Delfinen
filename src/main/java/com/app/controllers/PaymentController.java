package com.app.controllers;

import com.app.controllers.utils.Input;
import com.app.models.MemberModel;
import com.app.models.MemberNotFoundException;
import com.app.models.services.PaymentService;
import com.app.views.PaymentsView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PaymentController {

  private MemberController memberController = new MemberController();
  private ArrayList<String> approvedPaymentsIds = new ArrayList<>();
  private PaymentsView VIEW = new PaymentsView();
  private PaymentService paymentService;

  public PaymentController() {
    try {
      paymentService = new PaymentService("data/payment-requests/out.txt");
      approvedPaymentsIds = paymentService.getApprovedPayments();
    } catch (IOException e) {
      VIEW.printWarning("Could not load Competitions");
    }
  }

  public void updateMemberShip(ArrayList<MemberModel> members) {

    for (int i = 0; i < members.size(); i++) {
      if (members.get(i).getMemberships().size() != 0) {
        members.get(i).getLatestMembership().setPayed(true);
      }
    }
  }

  public void reviewPaymentFile() {
    String[] resultsToString = new String[approvedPaymentsIds.size()];
    for (int i = 0; i < approvedPaymentsIds.size(); i++) {
      try {
        MemberModel member = memberController.getMemberByID(approvedPaymentsIds.get(i));
        String id = member.getID();
        String name = member.getName();
        resultsToString[i] = String.join(";", id, name);
      } catch (MemberNotFoundException e) {
        String id = approvedPaymentsIds.get(i);
        resultsToString[i] = String.join(";", id, "Member does not exist");
      }
    }
    VIEW.displayPayments(resultsToString);
  }

  public void handlePayments(Scanner in) {
    reviewPaymentFile();
    VIEW.printInline("Do you wish to update the memberships of valid members [Y/N]: ");

    if (Input.promptYesNo(in)) {
      updateMemberShip(getValidPayments());
      VIEW.printSuccess("Memberships successfully updated!");
    } else {
      VIEW.printWarning("Canceled!");
    }
  }

  public ArrayList<MemberModel> getValidPayments() {

    ArrayList<MemberModel> result = new ArrayList<>();
    ArrayList<String> failedPayments = new ArrayList<>();

    for (int i = 0; i < approvedPaymentsIds.size(); i++) {
      try {
        MemberModel member = memberController.getMemberByID(approvedPaymentsIds.get(i));
        result.add(member);

      } catch (MemberNotFoundException e) {
        failedPayments.add(approvedPaymentsIds.get(i));
      }
    }
    // send til backup
    return result;
  }
}

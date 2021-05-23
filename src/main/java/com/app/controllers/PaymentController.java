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
  private final MemberController MEMBER_CONTROLLER = new MemberController();
  private final PaymentsView VIEW = new PaymentsView();
  private ArrayList<String> approvedPaymentsIds = new ArrayList<>();
  private PaymentService paymentService;

  public PaymentController() {
    try {
      String path = "data/payment-requests/";
      paymentService = new PaymentService(path);
      approvedPaymentsIds = paymentService.getApprovedPayments();
    } catch (IOException e) {
      VIEW.printWarning("The competitions could not be loaded.");
    }
  }

  private void updateMemberShip(ArrayList<MemberModel> members) {
    for (MemberModel member : members) {
      if (member.getMemberships().size() != 0) {
        member.getLatestMembership().setPayed(true);
      }
    }
  }

  private void reviewPaymentFile() {
    String[] resultsToString = new String[approvedPaymentsIds.size()];
    for (int i = 0; i < approvedPaymentsIds.size(); i++) {
      try {
        MemberModel member = MEMBER_CONTROLLER.getMemberByID(approvedPaymentsIds.get(i));
        String id = member.getID();
        String name = member.getName();
        resultsToString[i] = String.join(";", id, name);
      } catch (MemberNotFoundException e) {
        String id = approvedPaymentsIds.get(i);
        resultsToString[i] = String.join(";", id, "The member does not exist.");
      }
    }
    VIEW.displayPayments(resultsToString);
  }

  public void handlePayments() {
    reviewPaymentFile();
    VIEW.printInline("Would you like to update the memberships of valid members? [Y/n]: ");

    if (Input.promptYesNo()) {
      updateMemberShip(getValidPayments());
      VIEW.printSuccess("Memberships successfully updated.");
    } else {
      VIEW.printWarning("Canceled.");
    }
  }

  private ArrayList<MemberModel> getValidPayments() {

    ArrayList<MemberModel> result = new ArrayList<>();
    ArrayList<String> failedPayments = new ArrayList<>();

    for (String approvedPaymentsId : approvedPaymentsIds) {
      try {
        MemberModel member = MEMBER_CONTROLLER.getMemberByID(approvedPaymentsId);
        result.add(member);

      } catch (MemberNotFoundException e) {
        failedPayments.add(approvedPaymentsId);
      }
      crateBackupFile(failedPayments);
    }

    return result;
  }

  private void crateBackupFile(ArrayList<String> failedPayments) {
    try {
      paymentService.backupToFile(failedPayments);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

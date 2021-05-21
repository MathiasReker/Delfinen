package com.app.controllers;

import com.app.models.MemberModel;
import com.app.models.MemberNotFoundException;
import com.app.models.services.PaymentService;
import com.app.views.PaymentsView;

import java.io.IOException;
import java.util.ArrayList;

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

  public void updateMemberShip() {

    for (int i = 0; i < approvedPaymentsIds.size(); i++) {
      try {
        MemberModel member = memberController.getMemberByID(approvedPaymentsIds.get(i));
        member.getLatestMembership().setPayed(true);
      } catch (MemberNotFoundException e) {
        VIEW.printWarning("Could not update Payment Status for: " + approvedPaymentsIds.get(i));
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
      }
      catch (MemberNotFoundException e) {
        String id = approvedPaymentsIds.get(i);
        resultsToString[i] = String.join(";", id,"Does not exist");
      }
    }
    VIEW.displayPayments(resultsToString);
  }

  public static void main(String[] args) {
    PaymentController paymentController = new PaymentController();
    paymentController.reviewPaymentFile();

  }
}
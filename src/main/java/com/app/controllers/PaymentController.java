package com.app.controllers;

import com.app.models.MemberModel;
import com.app.models.MembershipModel;
import com.app.models.exceptions.MemberNotFoundException;
import com.app.models.services.ConfigService;
import com.app.models.services.PaymentService;
import com.app.views.PaymentsView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class PaymentController {
  private final MemberController MEMBER_CONTROLLER = new MemberController();
  private final PaymentsView VIEW = new PaymentsView();
  private ArrayList<String> approvedPaymentsIds = new ArrayList<>();
  private PaymentService paymentService;

  public PaymentController() {
    try {
      paymentService = new PaymentService(new ConfigService("paymentRequests").getPath());
      approvedPaymentsIds = paymentService.getApprovedPayments();
    } catch (IOException e) {
      VIEW.printWarning("The payment file could not be loaded.");
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
    String[][] resultsToString = new String[approvedPaymentsIds.size()][3];
    for (int i = 0; i < approvedPaymentsIds.size(); i++) {
      try {
        MemberModel member = MEMBER_CONTROLLER.getMemberByID(approvedPaymentsIds.get(i));
        String id = member.getId();
        String name = member.getName();
        resultsToString[i] = new String[] {id, name};
      } catch (MemberNotFoundException e) {
        String id = approvedPaymentsIds.get(i);
        resultsToString[i] = new String[] {id, null};
      }
    }
    VIEW.displayPayments(resultsToString);
  }

  public void handlePayments() {
    reviewPaymentFile();
    VIEW.printInline("Would you like to update the memberships of valid members? [Y/n]: ");

    if (InputController.promptYesNo()) {
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

  public void displayMembersInArrear() {
    ArrayList<MemberModel> unpaidMembers =
        MEMBER_CONTROLLER.getUnpaidMembers(MEMBER_CONTROLLER.getMembers());
    ArrayList<MemberModel> arrears = findMembersInArrear(unpaidMembers);

    int size = arrears.size();
    String[][] arrearsData = new String[size][3];
    for (int i = 0; i < size; i++) {
      String days = calcPeriod(LocalDate.now(),arrears.get(i).getLatestMembership().getStartingDate());
      arrearsData[i] =
          new String[] {arrears.get(i).getId(), arrears.get(i).getName(), days};
    }

    VIEW.displayArrears(arrearsData);
  }

  ArrayList<MemberModel> findMembersInArrear(ArrayList<MemberModel> members) {
    ArrayList<MemberModel> result = new ArrayList<>();

    for (MemberModel member : members) {
      ArrayList<MembershipModel> memberships = member.getMemberships();
      if (memberships.size() == 1) {
        if (memberships.get(0).getStartingDate().compareTo(LocalDate.now().plusDays(14)) < 0) {
          result.add(member);
        }
      } else {
        if (member.getLatestMembership().getStartingDate().compareTo((LocalDate.now().plusDays(14)))
            < 0) {
          result.add(member);
        }
      }
    }
    return result;
  }

  String calcPeriod(LocalDate date1, LocalDate date2){
    long test = ChronoUnit.DAYS.between(date1,date2);

    return String.valueOf(test);
  }

}

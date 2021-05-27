package com.app.controllers;

import com.app.models.MemberModel;
import com.app.models.MembershipModel;
import com.app.models.exceptions.MemberNotFoundException;
import com.app.models.services.ConfigService;
import com.app.models.services.PaymentRequestService;
import com.app.models.services.PaymentService;
import com.app.views.PaymentsView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class PaymentController {
  private final MemberController MEMBER_CONTROLLER = new MemberController();
  private final PaymentsView VIEW = new PaymentsView();
  private ArrayList<String> approvedPaymentsIds = new ArrayList<>();
  private PaymentService paymentService;

  /**
   * Update paid status on latest membership.
   *
   * @param members ArrayList<MemberModel>
   * @auther Mohamad
   */
  private void updatePaymentStatus(ArrayList<MemberModel> members) {
    for (MemberModel member : members) {
      if (member.getMemberships().size() != 0) {
        member.getLatestMembership().setPaid(true);
      }
    }
  }

  /**
   * Review payment file.
   *
   * @auther Mohamad
   */
  private void reviewPaymentFile() {
    VIEW.printTable(getPaymentHeader(), getPaymentContent());
  }

  private ArrayList<String> getRows(String approvedPaymentsId) {
    ArrayList<String> result = new ArrayList<>();

    try {
      MemberModel member = MEMBER_CONTROLLER.getMemberById(approvedPaymentsId);

      result.add(member.getId());
      result.add(member.getName());
    } catch (MemberNotFoundException e) {
      result.add(approvedPaymentsId);
      result.add(null);
    }

    return result;
  }

  private ArrayList<ArrayList<String>> getPaymentContent() {
    ArrayList<ArrayList<String>> result = new ArrayList<>();

    for (String approvedPaymentsId : approvedPaymentsIds) {
      result.add(getRows(approvedPaymentsId));
    }

    return result;
  }

  private String[] getPaymentHeader() {
    return new String[] {"ID", "Name"};
  }

  /**
   * Display payments and handle payment based on user input.
   *
   * @auther Mohamad
   */
  public void handlePayments() {
    try {
      paymentService = new PaymentService(new ConfigService("paymentRequestsPath").getPath());
      approvedPaymentsIds = paymentService.getApprovedPayments();
      reviewPaymentFile();
      VIEW.printInline("Update memberships of valid members [Y/n]: ");

      if (InputController.promptYesNo()) {
        updatePaymentStatus(getValidPayments());
        VIEW.printSuccess("Memberships successfully updated.");
        MEMBER_CONTROLLER.saveMembers();
      } else {
        VIEW.printWarning("Canceled.");
      }
    } catch (IOException e) {
      VIEW.printWarning("The payment file could not be loaded.");
    }
  }

  /**
   * Returns a new arraylist of valid payments and backups invalid payments.
   *
   * @return ArrayList<MemberModel>
   * @auther Mohamad
   */
  private ArrayList<MemberModel> getValidPayments() {
    ArrayList<MemberModel> result = new ArrayList<>();
    ArrayList<String> failedPayments = new ArrayList<>();

    for (String approvedPaymentsId : approvedPaymentsIds) {
      try {
        MemberModel member = MEMBER_CONTROLLER.getMemberById(approvedPaymentsId);
        result.add(member);

      } catch (MemberNotFoundException e) {
        failedPayments.add(approvedPaymentsId);
      }
      crateBackupFile(failedPayments);
    }

    return result;
  }

  /**
   * Create/write backup file.
   *
   * @param failedPayments ArrayList<String>
   * @auther Mohamad
   */
  private void crateBackupFile(ArrayList<String> failedPayments) {
    try {
      paymentService.backupToFile(failedPayments);
    } catch (IOException e) {
      VIEW.printWarning(e.getMessage());
    }
  }

  /**
   * Show members in arrears.
   *
   * @auther Andreas, Mohamad
   */
  public void displayMembersInArrears() {
    VIEW.printTable(getArrearsHeader(), getArrearsContent());
  }

  private String[] getArrearsHeader() {
    return new String[] {"ID", "Name", "Days"};
  }

  /** @auther Mathias, Mohamad */
  private ArrayList<String> getArrearsRows(MemberModel arrears) {
    ArrayList<String> result = new ArrayList<>();

    result.add(arrears.getId());
    result.add(arrears.getName());
    result.add(calcPeriod(LocalDate.now(), arrears.getLatestMembership().getStartingDate()));

    return result;
  }

  /** @auther Mathias, Mohamad */
  private ArrayList<ArrayList<String>> getArrearsContent() {
    ArrayList<ArrayList<String>> result = new ArrayList<>();

    ArrayList<MemberModel> unpaidMembers =
        MEMBER_CONTROLLER.getUnpaidMembers(MEMBER_CONTROLLER.getMembers());
    ArrayList<MemberModel> arrears = getMembersInArrears(unpaidMembers);

    for (MemberModel arrear : arrears) {
      result.add(getArrearsRows(arrear));
    }

    return result;
  }

  /**
   * Returns a list of members in arrears.
   *
   * @param members ArrayList<MemberModel>
   * @return ArrayList<MemberModel>
   * @auther Mohamad, Andreas
   */
  ArrayList<MemberModel> getMembersInArrears(ArrayList<MemberModel> members) {
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

  /**
   * Return amount of days between two dates.
   *
   * @param date1 LocalDate
   * @param date2 LocalDate
   * @return String
   * @auther Andreas
   */
  String calcPeriod(LocalDate date1, LocalDate date2) {
    long test = ChronoUnit.DAYS.between(date1, date2);

    return String.valueOf(test);
  }

  /**
   * Request payment for unpaid members.
   *
   * @auther Andreas
   */
  public void requestPaymentForUnpaidMembers() {
    try {
      ArrayList<MemberModel> unpaidMembers =
          MEMBER_CONTROLLER.getUnpaidMembers(MEMBER_CONTROLLER.getMembers());
      PaymentRequestService paymentRequester =
          new PaymentRequestService(new ConfigService("paymentRequestsPath").getPath() + "out.txt");
      unpaidMembers = MEMBER_CONTROLLER.removeMemberFromList(unpaidMembers);
      if (!unpaidMembers.isEmpty()) {
        VIEW.printInline("Send the payment requests [Y/n]: ");
        if (InputController.promptYesNo()) {
          paymentRequester.createPaymentRequest(unpaidMembers.toArray(new MemberModel[0]));
        }
      }

    } catch (IOException e) {
      VIEW.printWarning(e.getMessage());
    }
  }
}

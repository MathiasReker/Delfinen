package com.app.controllers;

import com.app.models.CompetitionModel;
import com.app.models.InputModel;
import com.app.models.MemberModel;
import com.app.views.InputView;

import java.util.ArrayList;
import java.util.Scanner;

public class InputController {
  private static final Scanner IN = new Scanner(System.in);
  private static final InputView VIEW = new InputView();

  public static boolean promptYesNo() {
    String input = IN.nextLine();
    while (true) {
      if (input.equalsIgnoreCase("y")) {
        return true;
      }
      if (input.equalsIgnoreCase("n")) {
        return false;
      }
      VIEW.printInlineWarning("Not a valid choice. Please try again: ");
      input = IN.nextLine();
    }
  }

  /**
   * Returns a valid integer. If the input is not an integer a warning will display until a valid
   * integer is given.
   *
   * @return a valid integer.
   */
  public static int validateInteger() {
    while (!IN.hasNextInt()) {
      VIEW.printInlineWarning("Not a valid choice. Please try again: ");
      IN.next();
    }

    return IN.nextInt();
  }

  /**
   * Returns a valid integer from a range. The range is defined as 0 to max. If the input is not
   * from the range, a warning will display until a valid integer from the range is given.
   *
   * @param max int that defines the maximum of the range.
   * @return a valid int from the range.
   */
  public static int validateOptionRange(int max) {
    while (true) {
      int result = validateInteger();

      if (InputModel.isValidRange(result, 1, max)) {
        IN.nextLine();
        return result;
      }
      VIEW.printInlineWarning("Not a valid choice. Please try again: ");
    }
  }

  public static String validateName() {
    while (true) {
      String result = IN.nextLine();
      if (InputModel.isValidName(result)) {
        return result;
      }
      VIEW.printInlineWarning("Not a valid name. Please try again: ");
    }
  }

  public static String validateMail() {
    while (true) {
      String result = IN.nextLine();
      if (InputModel.isValidMail(result)) {
        return result;
      }
      VIEW.printInlineWarning("Not a valid mail. Please try again: ");
    }
  }

  public static String validateDate() {
    String result = IN.nextLine();
    while (!InputModel.isValidDate(result)) {
      VIEW.printInlineWarning("Not a valid date. Please try again: ");
      result = IN.nextLine();
    }

    return result;
  }

  public static String validatePhoneNumber() {
    while (true) {
      String result = IN.nextLine().trim();
      if (InputModel.isValidPhoneNumber(result)) {
        return result;
      }
      VIEW.printInlineWarning("Not a valid phone number. Please try again: ");
    }
  }

  public static String validateMemberId(ArrayList<MemberModel> members) {
    while (true) {
      String result = IN.nextLine();

      if (result.equals("q")) {
        return null;
      }

      for (MemberModel m : members) { // TODO: move to model
        if (m.getID().equals(result)) {
          return result;
        }
      }

      VIEW.printInlineWarning("Not a valid ID. Please try again: ");
    }
  }

  public static CompetitionModel validateCompetitionsId(ArrayList<CompetitionModel> competitions) {
    while (true) {
      String result = IN.nextLine(); // TODO: refactor return String?

      if (result.equals("q")) {
        return null;
      }

      for (CompetitionModel c : competitions) {
        if (c.getId().equals(result)) {
          return c;
        }
      }

      VIEW.printInlineWarning("Not a valid ID. Please try again: ");
    }
  }

  /**
   * A method to validate that the time input we receive is a valid format
   *
   * @return returns a time as a LocalTime type
   */
  public static String validateCompetitionResultTime() {
    String result = IN.nextLine();
    while (!InputModel.isValidCompetitionResultTime("00:" + result)) {
      VIEW.printInlineWarning("Not a valid time. Please try again: ");
      result = IN.nextLine();
    }

    return result;
  }

  public static String validateCompetitionTime() {
    String result = IN.nextLine();
    while (!InputModel.isValidCompetitionTime(result)) {
      VIEW.printInlineWarning("Not a valid time. Please try again: ");
      result = IN.nextLine();
    }

    return result;
  }

  public static String anyString() {
    return IN.nextLine();
  }
}

package com.app.controllers.utils;

import com.app.models.MemberModel;
import com.app.models.ValidateModel;
import com.app.views.MenuView;

import java.util.ArrayList;
import java.util.Scanner;

public class Input { // TODO rename

  private static final MenuView VIEW = new MenuView();

  public static boolean promptYesNo(Scanner in) {
    String input = in.nextLine();
    while (true) {
      if (input.equalsIgnoreCase("y")) {
        return true;
      }
      if (input.equalsIgnoreCase("n")) {
        return false;
      }
      VIEW.printInlineWarning("Not a valid choice. Please try again: ");
      input = in.nextLine();
    }
  }

  /**
   * Returns a valid integer. If the input is not an integer a warning will display until a valid
   * integer is given.
   *
   * @return a valid integer.
   */
  public static int validateInteger(Scanner in) {
    while (!in.hasNextInt()) {
      VIEW.printInlineWarning("Not a valid choice. Please try again: ");
      in.next();
    }

    return in.nextInt();
  }

  /**
   * Returns a valid integer from a range. The range is defined as 0 to max. If the input is not
   * from the range, a warning will display until a valid integer from the range is given.
   *
   * @param max int that defines the maximum of the range.
   * @return a valid int from the range.
   */
  public static int validateOptionRange(Scanner in, int max) {
    while (true) {
      int result = validateInteger(in);

      if (ValidateModel.isValidRange(result, 1, max)) {
        in.nextLine();
        return result;
      }
      VIEW.printInlineWarning("Not a valid choice. Please try again: ");
    }
  }

  public static String validateName(Scanner in) {
    while (true) {
      String result = in.nextLine();
      if (ValidateModel.isValidName(result)) {
        return result;
      }
      VIEW.printInlineWarning("Not a valid name. Please try again: ");
    }
  }

  public static String validateMail(Scanner in) {
    while (true) {
      String result = in.nextLine();
      if (ValidateModel.isValidMail(result)) {
        return result;
      }
      VIEW.printInlineWarning("Not a valid mail. Please try again: ");
    }
  }

  public static String validateDate(Scanner in) {
    String result = in.nextLine();
    while (!ValidateModel.isValidDate(result)) {
      VIEW.printInlineWarning("Not a valid date. Please try again: ");
      result = in.nextLine();
    }

    return result;
  }

  public static String validatePhoneNumber(Scanner in) {
    while (true) {
      String result = in.nextLine().trim();
      if (ValidateModel.isValidPhoneNumber(result)) {
        return result;
      }
      VIEW.printInlineWarning("Not a valid phone number. Please try again: ");
    }
  }

  public static String validateMemberId(ArrayList<MemberModel> members, Scanner in) {
    while (true) {
      String result = in.nextLine();

      if (result.equals("q")) {
        return null;
      }

      for (MemberModel m : members) {
        if (m.getID().equals(result)) {
          return result;
        }
      }

      VIEW.printInlineWarning("Not a valid ID. Please try again: ");
    }
  }

  /**
   * A method to validate that the time input we receive is a valid format
   *
   * @param in a String with the time that needs to be parsed.
   * @return returns a time as a LocalTime type
   */
  public static String validateCompetitionResultTime(Scanner in) {
    String result = in.nextLine();
    while (!ValidateModel.isValidCompetitionResultTime("00:" + result)) {
      VIEW.printInlineWarning("Not a valid time. Please try again: ");
      result = in.nextLine();
    }

    return result;
  }

  public static String validateCompetitionTime(Scanner in) {
    String result = in.nextLine();
    while (!ValidateModel.isValidCompetitionTime(result)) {
      VIEW.printInlineWarning("Not a valid time. Please try again: ");
      result = in.nextLine();
    }

    return result;
  }
}

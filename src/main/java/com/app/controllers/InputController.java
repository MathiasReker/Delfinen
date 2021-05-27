package com.app.controllers;

import com.app.models.InputModel;
import com.app.models.MemberModel;
import com.app.models.SwimEventModel;
import com.app.views.InputView;

import java.util.ArrayList;
import java.util.Scanner;

public class InputController {
  private static final Scanner IN = new Scanner(System.in);
  private static final InputView VIEW = new InputView();

  /**
   * Returns true if yes, and false if no.
   *
   * @return boolean
   * @auther Mathias
   */
  static boolean promptYesNo() {
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
   * @auther Mathias
   */
  static int validateInteger() {
    while (!IN.hasNextInt()) {
      VIEW.printInlineWarning("Not a valid choice. Please try again: ");
      IN.nextLine();
    }

    return IN.nextInt();
  }

  /**
   * Returns a valid integer from a range. The range is defined as 0 to max. If the input is not
   * from the range, a warning will display until a valid integer from the range is given.
   *
   * @param max int that defines the maximum of the range.
   * @return a valid int from the range.
   * @auther Mathias
   */
  static int validateOptionRange(int max) {
    int result = validateInteger();
    while (!InputModel.isValidRange(result, 1, max)) {
      VIEW.printInlineWarning("Not a valid choice. Please try again: ");
      result = IN.nextInt();
      IN.nextLine();
    }

    IN.nextLine();
    return result;
  }

  /**
   * Returns a valid name.
   *
   * @return String
   * @auther Mathias
   */
  static String validateName() {
    String result = IN.nextLine();
    while (!InputModel.isValidName(result)) {
      VIEW.printInlineWarning("Not a valid name. Please try again: ");
      result = IN.nextLine();
    }

    return result;
  }

  /**
   * Returns a valid mail.
   *
   * @return String
   * @auther Mathias
   */
  static String validateMail() {
    String result = IN.nextLine();
    while (!InputModel.isValidMail(result)) {
      VIEW.printInlineWarning("Not a valid mail. Please try again: ");
      result = IN.nextLine();
    }

    return result;
  }

  /**
   * Returns a valid birthdate.
   *
   * @return String
   * @auther Mathias
   */
  static String validateBirthDate() {
    String result = IN.nextLine().replace("-", "/"); // "-" and "/"

    while (!InputModel.isValidBirthDate(result)) {
      VIEW.printInlineWarning("Not a valid date. Please try again: ");
      result = IN.nextLine();
    }

    return result;
  }

  /**
   * Returns a valid date.
   *
   * @return String
   * @auther Mathias
   */
  static String validateDate() {
    String result = IN.nextLine().replace("-", "/"); // "-" and "/"

    while (!InputModel.isValidDate(result)) {
      VIEW.printInlineWarning(
          "Not a valid date. Please try again, only dates in the future can be input: ");
      result = IN.nextLine();
    }

    return result;
  }

  /**
   * Returns a valid phone number.
   *
   * @return String
   * @auther Mathias
   */
  static String validatePhoneNumber() {
    while (true) {
      String result = IN.nextLine().trim();
      if (InputModel.isValidPhoneNumber(result)) {
        return result;
      }
      VIEW.printInlineWarning("Not a valid phone number. Please try again: ");
    }
  }

  /**
   * Returns a valid member ID.
   *
   * @param members ArrayList<MemberModel>
   * @return String
   * @auther Mathias, Mohamad
   */
  static String validateMemberId(ArrayList<MemberModel> members) {
    while (true) {
      String result = IN.nextLine();

      if (result.equals("q")) {
        return null;
      }

      if (InputModel.isValidMemberId(result, members)) {
        return result;
      }

      VIEW.printInlineWarning("Not a valid ID. Please try again: ");
    }
  }

  /**
   * Returns valid swim event.
   *
   * @param swimEvents ArrayList<SwimEventModel>
   * @return SwimEventModel|null
   * @auther Mathias, Mohamad, Jackie
   */
  static SwimEventModel validateSwimEvent(ArrayList<SwimEventModel> swimEvents) {
    while (true) {
      String result = IN.nextLine();
      if (result.equals("q")) {
        return null;
      }

      for (SwimEventModel swimEvent : swimEvents) {
        if (swimEvent.getId().equals(result)) {
          return swimEvent;
        }
      }

      VIEW.printInlineWarning("Not a valid ID. Please try again: ");
    }
  }

  static String validateAgeGroup() { // TODO: refactor -> use arrayOption
    String result = IN.nextLine();
    while (true) {
      if (result.equalsIgnoreCase("j")) {
        return result;
      }
      if (result.equalsIgnoreCase("s")) {
        return result;
      }
      VIEW.printInlineWarning("Not a valid choice. Please try again: ");
      result = IN.nextLine();
    }
  }

  /**
   * Returns a validate result time.
   *
   * @return String
   * @auther Mohamad
   */
  static String validateResultTime() {
    String result = IN.nextLine();
    while (!InputModel.isValidCompetitionResultTime("00:" + result)) {
      VIEW.printInlineWarning("Not a valid time. Please try again: ");
      result = IN.nextLine();
    }

    return result;
  }

  /**
   * Returns a valid swim event time.
   *
   * @return String
   * @auther Mohamad
   */
  static String validateSwimEventTime() {
    String result = IN.nextLine();
    while (!InputModel.isValidSwimEventTime(result)) {
      VIEW.printInlineWarning("Not a valid time. Please try again: ");
      result = IN.nextLine();
    }

    return result;
  }

  /**
   * Returns a valid placement.
   *
   * @return String
   * @auther Jackie
   */
  static String validatePlacement() {
    int placement = validateInteger();
    IN.nextLine();

    return String.valueOf(placement);
  }

  /**
   * Return any string.
   *
   * @return String
   * @auther Mathias
   */
  public static String anyString() {
    return IN.nextLine();
  }
}

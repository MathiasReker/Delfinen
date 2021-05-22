package com.app.controllers.utils;

import com.app.models.ValidateModel;
import com.app.views.MenuView;

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
}

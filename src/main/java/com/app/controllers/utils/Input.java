package com.app.controllers.utils;

import com.app.views.MenuView;

import java.util.Scanner;

public class Input { //TODO rename to something that makes sense

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
}

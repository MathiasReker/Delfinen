package com.app.controllers;

import com.app.controllers.menuactions.MenuActions;
import com.app.models.MenuModel;
import com.app.views.MenuView;

import java.util.Scanner;

public class MenuController {
  private final MenuModel MENU;
  private final Scanner SCANNER = new Scanner(System.in);
  private final MenuView MENU_VIEW;

  /**
   * MenuController.
   *
   * @param menuHeader String that describes the header of the menu.
   * @param leadText String that tells the user what to do.
   * @param menuActions String.
   */
  public MenuController(String menuHeader, String leadText, MenuActions[] menuActions) {
    MENU = new MenuModel(menuHeader, menuActions, leadText);
    MENU_VIEW = new MenuView();
  }

  /** Run the menu as long as running is true. */
  public void run() {
    boolean running = true;
    while (running) {
      MENU_VIEW.printMenuOptions(MENU.getMenuHeader(), MENU.getMenuActionMenuItems());
      MENU_VIEW.printInline(MENU.getLeadText());
      int input = validateNumberRange(MENU.getMenuActionMenuItems().length) - 1;
      MENU.getMenuItem(input).run();
      running = MENU.getMenuItem(input).isKeepRunning();
    }
  }

  /**
   * Returns a valid integer. If the input is not an integer a warning will display until a valid
   * integer is given.
   *
   * @return a valid integer.
   */
  private int validateNumber() {
    while (!SCANNER.hasNextInt()) {
      MENU_VIEW.printInlineWarning("Not a valid menu choice. Please try again: ");
      SCANNER.nextLine();
    }

    return SCANNER.nextInt();
  }

  /**
   * Returns a valid integer from a range. The range is defined as 0 to max. If the input is not
   * from inside the range, a warning will display until a valid integer from the range is given.
   *
   * @param max int that defines the maximum of the range.
   * @return int a valid integer from the range.
   */
  private int validateNumberRange(int max) {
    int result = validateNumber();

    while (result > max || result <= 0) {
      MENU_VIEW.printInlineWarning("Not a valid menu choice. Please try again: ");
      SCANNER.nextLine();
      result = validateNumber();
    }

    return result;
  }
}

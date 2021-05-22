package com.app.controllers;

import com.app.controllers.menuactions.MenuActions;
import com.app.controllers.utils.Input;
import com.app.models.MenuModel;
import com.app.views.MenuView;

import java.util.Scanner;

public class MenuController {
  private final MenuModel MENU;
  private final MenuView VIEW;

  /**
   * Menu controller.
   *
   * @param menuHeader String that describes the header of the menu.
   * @param leadText String that tells the user what to do.
   * @param menuActions String.
   */
  public MenuController(String menuHeader, String leadText, MenuActions[] menuActions) {
    MENU = new MenuModel(menuHeader, menuActions, leadText);
    VIEW = new MenuView();
  }

  /** Run the menu as long as running is true. */
  public void run() {
    boolean running = true;
    while (running) {
      VIEW.printMenuOptions(MENU.getMenuHeader(), MENU.getMenuActionMenuItems());
      VIEW.printInline(MENU.getLeadText());

      Scanner in = new Scanner(System.in);
      int input = Input.validateOptionRange(in, MENU.getMenuActionMenuItems().length) - 1;
      MENU.getMenuItem(input).run();
      running = MENU.getMenuItem(input).isKeepRunning();
    }
  }
}

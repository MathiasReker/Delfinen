package com.app.controllers;

import com.app.controllers.menuactions.MenuAction;
import com.app.models.MenuModel;
import com.app.views.MenuView;

import java.util.ArrayList;

public class MenuController {
  private final MenuModel MENU;
  private final MenuView VIEW;

  /**
   * Menu controller.
   *
   * @param menuHeader String that describes the header of the menu.
   * @param leadText String that tells the user what to do.
   * @param menuActions String.
   * @auther Mathias
   */
  public MenuController(String menuHeader, String leadText, MenuAction[] menuActions) {
    MENU = new MenuModel(menuHeader, menuActions, leadText);
    VIEW = new MenuView();
  }

  /**
   * Run the menu as long as running is true.
   *
   * @auther Mathias
   */
  public void run() {
    boolean running = true;
    while (running) {

      displayMenu();

      VIEW.printInline(MENU.getLeadText());

      int input = InputController.validateOptionRange(MENU.getMenuActionMenuItems().length) - 1;
      MENU.getMenuItem(input).run();
      running = MENU.getMenuItem(input).isKeepRunning();
    }
  }

  /**
   * Display menu.
   *
   * @auther Mathias
   */
  private void displayMenu() {
    String[] header = new String[] {"No.", MENU.getMenuHeader()};
    ArrayList<ArrayList<String>> content = new ArrayList<>();

    for (int i = 0; i < MENU.getMenuActionMenuItems().length; i++) {
      ArrayList<String> row = new ArrayList<>();
      row.add(String.valueOf(i + 1));
      row.add(MENU.getMenuActionMenuItems()[i]);
      content.add(row);
    }

    VIEW.printTable(header, content);
  }
}

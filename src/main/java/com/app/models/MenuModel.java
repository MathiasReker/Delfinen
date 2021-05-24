package com.app.models;

import com.app.controllers.menuactions.MenuAction;

public class MenuModel {
  private final MenuAction[] MENU_ACTIONS;
  private final String MENU_HEADER;
  private final String LOAD_TEXT;

  /**
   * Constructor that set attributes.
   *
   * @param menuHeader String that describes the header of the menu.
   * @param menuActions Array that includes all the menu items.
   * @param leadText String that tells the user what to do.
   */
  public MenuModel(String menuHeader, MenuAction[] menuActions, String leadText) {
    this.MENU_ACTIONS = menuActions;
    this.MENU_HEADER = menuHeader;
    this.LOAD_TEXT = leadText;
  }

  /**
   * Returns the menu item by an index.
   *
   * @param index int that tells the index position of the menu item.
   * @return menu item.
   */
  public MenuAction getMenuItem(int index) {
    return MENU_ACTIONS[index];
  }

  /**
   * Returns the lead text that tells the user what to do.
   *
   * @return String.
   */
  public String getLeadText() {
    return LOAD_TEXT;
  }

  /**
   * Returns the header of the menu.
   *
   * @return String.
   */
  public String getMenuHeader() {
    return MENU_HEADER;
  }

  /**
   * Returns a String[] of menu items.
   *
   * @return String.
   */
  public String[] getMenuActionMenuItems() {
    String[] result = new String[MENU_ACTIONS.length];
    for (int i = 0; i < result.length; i++) {
      result[i] = MENU_ACTIONS[i].getItemName();
    }

    return result;
  }
}

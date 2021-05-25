package com.app.controllers.menuactions;

public class ExitMenuAction extends MenuAction {
  /**
   * Stop the program from running.
   *
   * @param itemName String that describes the menu action.
   */
  public ExitMenuAction(String itemName) {
    super(itemName, false);
  }

  /** No business logic should be done as this menu action shut down the program. */
  @Override
  public void run() {}
}

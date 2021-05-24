package com.app.controllers.menuactions;

public abstract class MenuAction {
  private final String ITEM_NAME;
  private boolean keepRunning = true;

  /**
   * Set a name for the menu item.
   *
   * @param itemName String describes the item name in the menu.
   */
  public MenuAction(String itemName) {
    this.ITEM_NAME = itemName;
  }

  /**
   * Used to manually set keepRunning to false. This will force the menu to stop running.
   *
   * @param keepRunning boolean that indicates if the menu will run.
   * @param itemName String that describes the item name in the menu.
   */
  public MenuAction(String itemName, boolean keepRunning) {
    this.keepRunning = keepRunning;
    this.ITEM_NAME = itemName;
  }

  /** The run method will run the business logic of the menu action. */
  public abstract void run();

  /**
   * Returns whether or not to keep running the menu.
   *
   * @return boolean that indicates if the menu will run.
   */
  public boolean isKeepRunning() {
    return keepRunning;
  }

  /**
   * Returns the name of the menu item.
   *
   * @return String that contains the name of the menu item.
   */
  public String getItemName() {
    return ITEM_NAME;
  }
}

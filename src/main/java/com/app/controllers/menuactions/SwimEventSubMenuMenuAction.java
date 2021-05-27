package com.app.controllers.menuactions;

import com.app.controllers.MenuController;

public class SwimEventSubMenuMenuAction extends MenuAction {
  public SwimEventSubMenuMenuAction(String itemName) {
    super(itemName);
  }

  @Override
  public void run() {
    new MenuController("Swim Event Management", "Please choose an option: ", menu()).run();
  }

  MenuAction[] menu() {
    return new MenuAction[] {
      new CompetitionSubMenuAction("Competition management"),
      new PracticeMenuAction("Practice Management"),
      new ExitMenuAction("Back")
    };
  }
}

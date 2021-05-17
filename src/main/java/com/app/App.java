package com.app;

import com.app.controllers.MenuController;
import com.app.controllers.menuactions.ExitMenuAction;
import com.app.controllers.menuactions.MemberMenuAction;
import com.app.controllers.menuactions.MenuActions;

public class App {
  /** Build the menu. */
  void menu() {
    // TODO: Add menu actions
    new MenuController("Main Menu", "Please choose an option: ", menuActions()).run();
  }

  MenuActions[] menuActions() {
    return new MenuActions[] {
        new MemberMenuAction("Create new member"),
        new ExitMenuAction("Exit")
    };
  }
}

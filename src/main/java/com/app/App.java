package com.app;

import com.app.controllers.MenuController;
import com.app.controllers.menuactions.ExitMenuAction;
import com.app.controllers.menuactions.MenuActions;

public class App {
  /** Build the menu. */
  void run() {
    MenuActions[] menu = {new ExitMenuAction("Exit")};

    new MenuController("Main Menu", "Please choose an option: ", menu).run();
  }
}

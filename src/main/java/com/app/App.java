package com.app;

import com.app.controllers.MenuController;
import com.app.controllers.menuactions.*;

public class App {
  /** Build the menu. */
  void menu() {
    // TODO: Add menu actions
    new MenuController("Main Menu", "Please choose an option: ", menuActions()).run();
  }

  MenuActions[] menuActions() {
    return new MenuActions[] {
      new MemberMenuAction("Member management"),
      new CompetitionMenuAction("Competition management"),
      new TopResultsMenuAction("Display top 5"),
      new ExitMenuAction("Exit"),
    };
  }
}

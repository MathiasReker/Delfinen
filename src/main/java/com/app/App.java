package com.app;

import com.app.controllers.MenuController;
import com.app.controllers.menuactions.*;

public class App {
  /** Build the menu. */
  void menu() {
    new MenuController("Main Menu", "Please choose an option: ", menuActions()).run();
  }

  MenuActions[] menuActions() {
    return new MenuActions[] {
      new MemberSubMenuMenuAction("Member management"),
      new CompetitionSubMenuMenuAction("Competition management"),
      new PaymentSubMenuMenuAction("Payment Management"),
      new TopResultsMenuAction("Display top 5"),
      new ExitMenuAction("Exit"),
    };
  }
}

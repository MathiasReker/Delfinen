package com.app;

import com.app.controllers.MenuController;
import com.app.controllers.menuactions.*;

public class App {
  void menu() {
    new MenuController("Main Menu", "Please choose an option: ", menuActions()).run();
  }

  MenuAction[] menuActions() {
    return new MenuAction[] {
      new MemberSubMenuMenuAction("Member Management"),
      new SwimEventSubMenuMenuAction("Swim Event Management"),
      new PaymentSubMenuMenuAction("Payment Management"),
      new Top5ResultsMenuAction("Display top 5"),
      new ExitMenuAction("Exit"),
    };
  }
}

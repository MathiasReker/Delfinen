package com.app.views;

import com.app.views.utils.ColorKey;
import com.app.views.utils.ColorText;

public class MenuView extends View {
  /**
   * Print a menu to the console with options from the String[] menuActions.
   *
   * @param header String that describes the header of the menu.
   * @param menuAction String[] that includes all the menu items.
   */
  public void printMenuOptions(String header, String[] menuAction) {
    String symbol1 = "┌";
    String symbol2 = "─";
    String symbol3 = "┐";
    String symbol4 = "│";
    String symbol5 = "┬";
    String symbol6 = "┤";
    String symbol7 = "└";
    String symbol8 = "┴";
    String symbol9 = "┘";
    String symbol10 = "├";

    // Create the header
    System.out.println(symbol1 + symbol2.repeat(28) + symbol3);
    ColorText headerFormatted = new ColorText(header.toUpperCase(), ColorKey.BLUE_BOLD_BRIGHT);
    System.out.printf("%2$s %1$-37s %2$s%n", headerFormatted, symbol4);
    System.out.println(symbol10 + symbol2.repeat(4) + symbol5 + symbol2.repeat(23) + symbol6);

    // Create the body
    for (int i = 0; i < menuAction.length; i++) {
      ColorText menuActionFormatted = new ColorText(menuAction[i], ColorKey.BLUE_BRIGHT);
      ColorText numberFormatted = new ColorText(i + 1, ColorKey.WHITE_BRIGHT);
      System.out.printf(
          "%3$s %1$-13s %3$s %2$-32s %3$s%n", numberFormatted, menuActionFormatted, symbol4);
    }
    System.out.println(symbol7 + symbol2.repeat(4) + symbol8 + symbol2.repeat(23) + symbol9);
  }
}

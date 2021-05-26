package com.app.views;

import com.app.views.types.ColorKeyType;
import com.app.views.types.ColorTextType;

import java.util.ArrayList;

abstract class View {
  public void printInline(String text) {
    System.out.print(new ColorTextType(text, ColorKeyType.WHITE_BRIGHT));
  }

  public void print(String text) {
    System.out.println(new ColorTextType(text, ColorKeyType.WHITE_BRIGHT));
  }
  /**
   * Print a colored text followed by a new line.
   *
   * @param text String of the output.
   * @param color ColorKey of the color.
   */
  public void print(String text, ColorKeyType color) {
    System.out.println(new ColorTextType(text, color));
  }

  /**
   * Print a text of the color green followed by a new line.
   *
   * @param text String of the output.
   */
  public void printSuccess(String text) {
    System.out.println(new ColorTextType(text, ColorKeyType.GREEN_BRIGHT));
  }

  /**
   * Print a text of the color red followed by a new line.
   *
   * @param text String of the output.
   */
  public void printWarning(String text) {
    System.out.println(new ColorTextType(text, ColorKeyType.RED));
  }

  /**
   * Print a text of the color red.
   *
   * @param text String of the output.
   */
  public void printInlineWarning(String text) {
    System.out.print(new ColorTextType(text, ColorKeyType.RED));
  }

  public void displayOptions(String[] text) {
    String[] result = new String[text.length];

    for (int i = 0; i < text.length; i++) {
      result[i] = text[i] + " [" + (i + 1) + "]";
    }

    printInline(String.join(", ", result) + ": ");
  }

  public void printTable(String[] headerIn, ArrayList<ArrayList<String>> bodyIn) {
    new TableView(headerIn, bodyIn).printTable();
  }
}

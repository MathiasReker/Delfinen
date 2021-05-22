package com.app.views;

import com.app.views.utils.ColorKey;
import com.app.views.utils.ColorText;

abstract class View {
  public void printInline(String text) {
    System.out.print(new ColorText(text, ColorKey.WHITE_BRIGHT));
  }

  public void print(String text) {
    System.out.println(new ColorText(text, ColorKey.WHITE_BRIGHT));
  }
  /**
   * Print a colored text followed by a new line.
   *
   * @param text String of the output.
   * @param color ColorKey of the color.
   */
  public void print(String text, ColorKey color) {
    System.out.println(new ColorText(text, color));
  }

  /**
   * Print a text of the color green followed by a new line.
   *
   * @param text String of the output.
   */
  public void printSuccess(String text) {
    System.out.println(new ColorText(text, ColorKey.GREEN_BRIGHT));
  }

  /**
   * Print a text of the color red followed by a new line.
   *
   * @param text String of the output.
   */
  public void printWarning(String text) {
    System.out.println(new ColorText(text, ColorKey.RED));
  }

  /**
   * Print a text of the color red.
   *
   * @param text String of the output.
   */
  public void printInlineWarning(String text) {
    System.out.print(new ColorText(text, ColorKey.RED));
  }

  public void displayOptions(String[] text) {
    String[] result = new String[text.length];

    for (int i = 0; i < text.length; i++) {
      result[i] = text[i] + " [" + (i + 1) + "]";
    }

    printInline(String.join(", ", result) + ": ");
  }
}

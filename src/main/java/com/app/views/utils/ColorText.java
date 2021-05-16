package com.app.views.utils;

public class ColorText {
  private final String TEXT;
  private final ColorKey COLOR;

  /**
   * Set the text and color attributes.
   *
   * @param text String.
   * @param color ColorKey.
   */
  public ColorText(String text, ColorKey color) {
    this.TEXT = text;
    this.COLOR = color;
  }

  /**
   * Set the text and color attributes.
   *
   * @param number int.
   * @param color ColorKey.
   */
  public ColorText(int number, ColorKey color) {
    this.TEXT = String.valueOf(number);
    this.COLOR = color;
  }

  /**
   * Returns a colored string.
   *
   * @return String.
   */
  @Override
  public String toString() {
    return COLOR + TEXT + ColorKey.RESET;
  }
}

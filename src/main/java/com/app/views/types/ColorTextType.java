package com.app.views.types;

/** @auther Mathias */
public class ColorTextType {
  private final String TEXT;
  private final ColorKeyType COLOR;

  /**
   * Set the text and color attributes.
   *
   * @param text String.
   * @param color ColorKey.
   * @auther Mathias
   */
  public ColorTextType(String text, ColorKeyType color) {
    this.TEXT = text;
    this.COLOR = color;
  }

  /**
   * Set the text and color attributes.
   *
   * @param number int.
   * @param color ColorKey.
   * @auther Mathias
   */
  public ColorTextType(int number, ColorKeyType color) {
    this.TEXT = String.valueOf(number);
    this.COLOR = color;
  }

  /**
   * Returns a colored string.
   *
   * @return String.
   * @auther Mathias
   */
  @Override
  public String toString() {
    return COLOR + TEXT + ColorKeyType.RESET;
  }
}

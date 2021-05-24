package com.app.views.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ColorTextTypeTest {
  ColorTextType colorText = new ColorTextType("Test", ColorKeyType.BLUE);

  @Test
  public void testToString() {
    String result = colorText.toString();
    Assertions.assertEquals("\033[0;34mTest\033[0m", result);
  }
}

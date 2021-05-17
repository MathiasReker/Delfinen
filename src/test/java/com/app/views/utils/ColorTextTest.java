package com.app.views.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ColorTextTest {
  ColorText colorText = new ColorText("Test", ColorKey.BLUE);

  @Test
  public void testToString() {
    String result = colorText.toString();
    Assertions.assertEquals("\033[0;34mTest\033[0m", result);
  }
}

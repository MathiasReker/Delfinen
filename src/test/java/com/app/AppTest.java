package com.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

public class AppTest {
  @Test
  public void testRunException() {
    // Avoid the menu to be shown on the screen
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    // Input -1 in the menu
    String input = "-1";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    // Expect the test to throw exception
    Assertions.assertThrows(NoSuchElementException.class, () -> new App().menu());
  }
}

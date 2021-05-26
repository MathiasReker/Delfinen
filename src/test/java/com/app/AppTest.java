package com.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

public class AppTest {
  @Test
  public void testRun() {
    // Avoid the menu to be shown on the screen
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    // Input exit key
    int lastMenuItem = new App().menuActions().length;
    String input = String.valueOf(lastMenuItem);

    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    // Run the menu
    // new App().menu(); // TODO: fix
  }

  @Test
  public void testRunException() {
    // Avoid the menu to be shown on the screen
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    // Input -1 in the menu
    String input = "-1\n";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    // Expect the test to throw exception
    Assertions.assertThrows(NoSuchElementException.class, () -> new App().menu());
  }

  @Test
  public void TestExitMenuAction() {
    // Avoid the menu to be shown on the screen
    PrintStream stdout = System.out;
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(outContent);
    System.setOut(ps);

    // Input "5" for exit
    InputStream stdin = System.in;
    String input = "5\n";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    new App().menu();

    System.setIn(stdin);
    System.setOut(stdout);

    String content = outContent.toString(StandardCharsets.UTF_8);
    String[] contents = content.split("\n");
    String expected = "\u001B[0;97mPlease choose an option: \u001B[0m";

    Assertions.assertEquals(expected, contents[contents.length - 1]);
  }
}

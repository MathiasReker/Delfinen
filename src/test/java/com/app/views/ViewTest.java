package com.app.views;

import com.app.views.types.ColorKeyType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ViewTest {
  View view = new MenuView();

  @Test
  public void testPrintInline() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    view.printInline("Test");

    Assertions.assertEquals("\u001B[0;97mTest\u001B[0m", outContent.toString());
  }

  @Test
  public void testPrint() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    view.print("Test", ColorKeyType.YELLOW_BOLD);

    Assertions.assertEquals("[1;33mTest\u001B[0m", outContent.toString().trim());
  }

  @Test
  public void testPrintError() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    view.print("Test", ColorKeyType.YELLOW_BOLD);

    Assertions.assertNotEquals("Test", outContent.toString().trim());
  }

  @Test
  public void testPrintSuccess() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    view.printSuccess("Test");

    Assertions.assertEquals("[0;92mTest\u001B[0m", outContent.toString().trim());
  }

  @Test
  public void testPrintSuccessError() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    view.printSuccess("Test");

    Assertions.assertNotEquals("Test", outContent.toString().trim());
  }

  @Test
  public void testPrintWarning() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    view.printWarning("Test");

    Assertions.assertEquals("[0;31mTest\u001B[0m", outContent.toString().trim());
  }

  @Test
  public void testPrintWarningError() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    view.printWarning("Test");

    Assertions.assertNotEquals("Test", outContent.toString().trim());
  }

  @Test
  public void testPrintInlineWarning() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    view.printInlineWarning("Test");

    Assertions.assertEquals("\u001B[0;31mTest\u001B[0m", outContent.toString());
  }

  @Test
  public void testPrintInlineWarningNotError() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    view.printInlineWarning("Test");

    Assertions.assertNotEquals("Test", outContent.toString());
  }
}

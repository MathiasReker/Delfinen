package com.app.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MembershipModelTest {
  MembershipModel test;

  private void setup() {
    test = new MembershipModel("Test");
    test.setStartingDate(LocalDate.parse("01-01-2020", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    test.setExpiringDate(LocalDate.parse("01-02-2020", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
  }

  @Test
  public void testSetActive() {
    setup();

    test.setActive(true);

    assertTrue(test.isActive());
  }

  @Test
  public void testSetPayed() {
    setup();

    test.setPaid(false);

    assertFalse(test.isPaid());
  }

  @Test
  public void testGetID() {
    setup();

    Assertions.assertEquals("Test", test.getID());
  }

  @Test
  public void testGetStartingDate() {
    setup();

    String expected = "2020-01-01";

    Assertions.assertEquals(expected, test.getStartingDate().toString());
  }

  @Test
  public void testGetExpiringDate() {
    setup();

    String expected = "2020-02-01";

    Assertions.assertEquals(expected, test.getExpiringDate().toString());
  }
}

package com.app.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MembershipModelTest {
  MembershipModel test;

  @BeforeEach
  public void testSetup() {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    test =
        new MembershipModel(
            "Test",
            LocalDate.parse("01-01-2020", format),
            LocalDate.parse("01-02-2020", format),
            true,
            false);
  }

  @Test
  public void testSetActive() {
    test.setActive(true);

    assertTrue(test.isActive());
  }

  @Test
  public void testSetPayed() {
    test.setPayed(false);

    assertFalse(test.isPayed());
  }

  @Test
  public void testGetID() {
    Assertions.assertEquals("Test", test.getID());
  }

  @Test
  public void testGetStartingDate() {
    String expected = "2020-01-01";

    Assertions.assertEquals(expected, test.getStartingDate().toString());
  }

  @Test
  public void testGetExpiringDate() {
    String expected = "2020-02-01";

    Assertions.assertEquals(expected, test.getExpiringDate().toString());
  }
}

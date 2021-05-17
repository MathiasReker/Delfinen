package com.app.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class MembershipModelTest {
  MembershipModel test;

  @BeforeEach
  public void TestSetup() {
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
  public void setActive() {
    test.setActive(true);

    Assertions.assertEquals(true,test.isActive());
  }

  @Test
  public void setPayed() {
    test.setPayed(false);

    Assertions.assertEquals(false,test.isPayed());
  }

  @Test
  public void getID() {
    Assertions.assertEquals("Test",test.getID());
  }

  @Test
  public void TestGetStartingDate(){
    String expected = "2020-01-01";

    Assertions.assertEquals(expected,test.getStartingDate().toString());
  }

  @Test
  public void TestGetExpiringDate(){
    String expected = "2020-02-01";

    Assertions.assertEquals(expected,test.getExpiringDate().toString());
  }

}

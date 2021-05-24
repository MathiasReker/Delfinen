package com.app.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CompetitionModelTest {

  @Test
  public void testValidGetId() {
    CompetitionModel competitionModel =
        new CompetitionModel(
            "testID",
            "Test name",
            LocalDate.parse("10/10/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            LocalTime.parse("10:00"));
    String expected = "testID";

    Assertions.assertEquals(expected, competitionModel.getId());
  }

  @Test
  public void testInvalidGetId() {
    CompetitionModel competitionModel =
        new CompetitionModel(
            "testID",
            "Test name",
            LocalDate.parse("10/10/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            LocalTime.parse("10:00"));
    String expected = "fakeID";

    Assertions.assertNotEquals(expected, competitionModel.getId());
  }

  @Test
  public void testValidGetName() {
    CompetitionModel competitionModel =
        new CompetitionModel(
            "testID",
            "Test name",
            LocalDate.parse("10/10/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            LocalTime.parse("10:00"));
    String expected = "Test name";

    Assertions.assertEquals(expected, competitionModel.getName());
  }

  @Test
  public void testInvalidGetName() {
    CompetitionModel competitionModel =
        new CompetitionModel(
            "testID",
            "Test name",
            LocalDate.parse("10/10/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            LocalTime.parse("10:00"));
    String expected = "Fake name";

    Assertions.assertNotEquals(expected, competitionModel.getName());
  }

  @Test
  public void testValidGetDate() {
    CompetitionModel competitionModel =
        new CompetitionModel(
            "testID",
            "Test name",
            LocalDate.parse("10/10/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            LocalTime.parse("10:00"));
    LocalDate expected = LocalDate.parse("10/10/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    Assertions.assertEquals(expected, competitionModel.getStartDate());
  }

  @Test
  public void testInvalidGetDate() {
    CompetitionModel competitionModel =
        new CompetitionModel(
            "testID",
            "Test name",
            LocalDate.parse("10/10/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            LocalTime.parse("10:00"));
    LocalDate expected = LocalDate.parse("10/11/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    Assertions.assertNotEquals(expected, competitionModel.getStartDate());
  }

  @Test
  public void testValidGetTime() {
    CompetitionModel competitionModel =
        new CompetitionModel(
            "testID",
            "Test name",
            LocalDate.parse("10/10/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            LocalTime.parse("10:00"));
    LocalTime expected = LocalTime.parse("10:00");

    Assertions.assertEquals(expected, competitionModel.getStartTime());
  }

  @Test
  public void testInvalidGetTime() {
    CompetitionModel competitionModel =
        new CompetitionModel(
            "testID",
            "Test name",
            LocalDate.parse("10/10/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            LocalTime.parse("10:00"));
    LocalTime expected = LocalTime.parse("11:00");

    Assertions.assertNotEquals(expected, competitionModel.getStartTime());
  }
}
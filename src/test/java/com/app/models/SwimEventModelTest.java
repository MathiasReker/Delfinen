package com.app.models;

import com.app.models.types.AgeGroupType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SwimEventModelTest {

  @Test
  public void testValidGetId() {
    SwimEventModel swimEventModel =
        new SwimEventModel(
            "testID",
            "Test name",
            LocalDate.parse("10/10/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            LocalTime.parse("10:00"),
            false,
            AgeGroupType.JUNIOR);
    String expected = "testID";

    Assertions.assertEquals(expected, swimEventModel.getId());
  }

  @Test
  public void testInvalidGetId() {
    SwimEventModel swimEventModel =
        new SwimEventModel(
            "testID",
            "Test name",
            LocalDate.parse("10/10/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            LocalTime.parse("10:00"),
            false,
            AgeGroupType.JUNIOR);
    String expected = "fakeID";

    Assertions.assertNotEquals(expected, swimEventModel.getId());
  }

  @Test
  public void testValidGetName() {
    SwimEventModel swimEventModel =
        new SwimEventModel(
            "testID",
            "Test name",
            LocalDate.parse("10/10/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            LocalTime.parse("10:00"),
            false,
            AgeGroupType.JUNIOR);
    String expected = "Test name";

    Assertions.assertEquals(expected, swimEventModel.getName());
  }

  @Test
  public void testInvalidGetName() {
    SwimEventModel swimEventModel =
        new SwimEventModel(
            "testID",
            "Test name",
            LocalDate.parse("10/10/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            LocalTime.parse("10:00"),
            false,
            AgeGroupType.JUNIOR);
    String expected = "Fake name";

    Assertions.assertNotEquals(expected, swimEventModel.getName());
  }

  @Test
  public void testValidGetDate() {
    SwimEventModel swimEventModel =
        new SwimEventModel(
            "testID",
            "Test name",
            LocalDate.parse("10/10/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            LocalTime.parse("10:00"),
            false,
            AgeGroupType.JUNIOR);
    LocalDate expected = LocalDate.parse("10/10/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    Assertions.assertEquals(expected, swimEventModel.getStartDate());
  }

  @Test
  public void testInvalidGetDate() {
    SwimEventModel swimEventModel =
        new SwimEventModel(
            "testID",
            "Test name",
            LocalDate.parse("10/10/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            LocalTime.parse("10:00"),
            false,
            AgeGroupType.JUNIOR);
    LocalDate expected = LocalDate.parse("10/11/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    Assertions.assertNotEquals(expected, swimEventModel.getStartDate());
  }

  @Test
  public void testValidGetTime() {
    SwimEventModel swimEventModel =
        new SwimEventModel(
            "testID",
            "Test name",
            LocalDate.parse("10/10/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            LocalTime.parse("10:00"),
            false,
            AgeGroupType.JUNIOR);
    LocalTime expected = LocalTime.parse("10:00");

    Assertions.assertEquals(expected, swimEventModel.getStartTime());
  }

  @Test
  public void testInvalidGetTime() {
    SwimEventModel swimEventModel =
        new SwimEventModel(
            "testID",
            "Test name",
            LocalDate.parse("10/10/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            LocalTime.parse("10:00"),
            false,
            AgeGroupType.JUNIOR);
    LocalTime expected = LocalTime.parse("11:00");

    Assertions.assertNotEquals(expected, swimEventModel.getStartTime());
  }
}

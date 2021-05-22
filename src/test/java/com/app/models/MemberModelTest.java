package com.app.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MemberModelTest {
  MemberModel test = new MemberModel();

  @Test
  public void testSetID() {
    test.setID("1");
    Assertions.assertEquals("1", test.getID());
  }

  @Test
  public void testSetName() {
    test.setName("NAME");

    Assertions.assertEquals("NAME", test.getName());
  }

  @Test
  public void testSetBirthdate() {
    String dateFormat = "dd-MM-yyyy";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
    test.setBirthdate(LocalDate.parse("10-11-2020", formatter));
    String actual = test.getBirthdate().toString();

    Assertions.assertEquals("2020-11-10", actual);
  }

  @Test
  public void testSetGender() {
    test.setGender(GenderModel.FEMALE);
    GenderModel expected = GenderModel.FEMALE;

    Assertions.assertEquals(expected, test.getGender());
  }

  @Test
  public void testSetCompetitive() {
    test.setCompetitive(true);

    Assertions.assertTrue(test.isCompetitive());
  }

  @Test
  public void testSetPhoneNumber() {
    test.setPhoneNumber("12345678");

    Assertions.assertEquals("12345678", test.getPhoneNumber());
  }

  @Test
  public void testSetMail() {
    test.setMail("test@test.com");

    Assertions.assertEquals("test@test.com", test.getMail());
  }

  @Test
  public void testAddMembership() {
    test.addMembership(new MembershipModel());
    int expectedMembershipLength = 1;

    Assertions.assertEquals(expectedMembershipLength, test.getMemberships().size());
  }

  @Test
  public void testAddDiscipline() {
    test.addDiscipline(new DisciplineModel(100, "FreeStyle"));
    int expectedMembershipLength = 1;

    Assertions.assertEquals(expectedMembershipLength, test.getDisciplines().size());
  }

  @Test
  public void testGetCreationTime() {
    LocalDate expected = LocalDate.now();

    Assertions.assertEquals(expected, test.getCreationDate());
  }

  @Test
  public void testCreateMemberFromFileConstructor() {
    test =
        new MemberModel(
            "test",
            "test",
            LocalDate.parse("10/11/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            GenderModel.FEMALE,
            "12345678",
            "test@test.com",
            true,
            new ArrayList<>(),
            LocalDate.parse("10/01/1990", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            new ArrayList<>());

    Assertions.assertEquals("test", test.getName());
  }
}

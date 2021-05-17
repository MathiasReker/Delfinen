package com.app.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MemberModelTest {

  MemberModel test;

  @BeforeEach
  void testSetup() {
    test = new MemberModel();
  }

  @Test
  void testSetID() {
    test.setID("1");
    Assertions.assertEquals("1", test.getID());
  }

  @Test
  void testSetName() {
    test.setName("NAME");

    Assertions.assertEquals("NAME", test.getName());
  }

  @Test
  void testSetBirthdate() {
    test.setBirthdate("10-11-2020");
    String actual = test.getBirthdate().toString();

    Assertions.assertEquals("2020-11-10", actual);
  }

  @Test
  void testSetGender() {
    test.setGender(GenderModel.FEMALE);
    GenderModel expected = GenderModel.FEMALE;

    Assertions.assertEquals(expected, test.getGender());
  }

  @Test
  void testSetCompetitive() {
    test.setCompetitive(true);

    Assertions.assertEquals(true, test.isCompetitive());
  }

  @Test
  void testSetPhoneNumber() {
    test.setPhoneNumber("1234");

    Assertions.assertEquals("1234", test.getPhoneNumber());
  }

  @Test
  void TestAddMembership() {
    test.addMembership(new MembershipModel());
    int expectedMembershipLength = 1;

    Assertions.assertEquals(expectedMembershipLength, test.getMemberships().size());
  }

  @Test
  void TestAddDiscipline() {
    test.addDiscipline(new DisciplineModel(100, "FreeStyle"));
    int expectedMembershipLength = 1;

    Assertions.assertEquals(expectedMembershipLength, test.getDisciplines().size());
  }

  @Test
  void TestGetCreationTime() {
    LocalDate expected = LocalDate.now();

    Assertions.assertEquals(expected, test.getCreationDate());
  }

  @Test
  void CreateMemberFromFileConstructor() {
    test =
        new MemberModel(
            "test",
            "test",
            LocalDate.parse("10-11-2020", DateTimeFormatter.ofPattern("dd-MM-yyyy")),
            GenderModel.FEMALE,
            "test",
            true,
            new ArrayList<DisciplineModel>(),
            LocalDate.parse("10-01-1990", DateTimeFormatter.ofPattern("dd-MM-yyyy")),
            new ArrayList<MembershipModel>());

    Assertions.assertEquals("test", test.getName());
  }
}

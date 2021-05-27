package com.app.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValidateModelTest {

  @Test
  public void testIsValidDate() {
    boolean result = InputModel.isValidBirthDate("01/01/1992");
    Assertions.assertTrue(result);
  }

  @Test
  public void testIsValidDate2() {
    boolean result = InputModel.isValidBirthDate("1/1/2020");
    Assertions.assertFalse(result);
  }

  @Test
  public void testIsValidDate3() {
    boolean result = InputModel.isValidBirthDate("");
    Assertions.assertFalse(result);
  }

  @Test
  public void testIsValidName() {
    boolean result = InputModel.isValidName("Mathias d'Arras");
    Assertions.assertTrue(result);
  }

  @Test
  public void testIsValidName2() {
    boolean result = InputModel.isValidName("Martin Luther King, Jr.");
    Assertions.assertTrue(result);
  }

  @Test
  public void testIsValidName3() {
    boolean result = InputModel.isValidName("Hector Sausage-Hausen");
    Assertions.assertTrue(result);
  }

  @Test
  public void testIsValidName4() {
    boolean result = InputModel.isValidName("");
    Assertions.assertFalse(result);
  }

  @Test
  public void testIsValidName5() {
    boolean result = InputModel.isValidName("Test0");
    Assertions.assertFalse(result);
  }

  @Test
  public void testIsValidName6() {
    boolean result = InputModel.isValidName("X Æ A-12");
    Assertions.assertFalse(result);
  }

  @Test
  public void testIsValidMail() {
    boolean result = InputModel.isValidMail("test@domain.com");
    Assertions.assertTrue(result);
  }

  @Test
  public void testIsValidMail2() {
    boolean result = InputModel.isValidMail("test@domain.co.in");
    Assertions.assertTrue(result);
  }

  @Test
  public void testIsValidMail3() {
    boolean result = InputModel.isValidMail("test.name@domain.com");
    Assertions.assertTrue(result);
  }

  @Test
  public void testIsValidMail4() {
    boolean result = InputModel.isValidMail("user_name@domain.com");
    Assertions.assertTrue(result);
  }

  @Test
  public void testIsValidMail5() {
    boolean result = InputModel.isValidMail("test@domain.co.in");
    Assertions.assertTrue(result);
  }

  @Test
  public void testIsValidMail6() {
    boolean result = InputModel.isValidMail(".test@domain.com");
    Assertions.assertFalse(result);
  }

  @Test
  public void testIsValidMail7() {
    boolean result = InputModel.isValidMail("test@domain.com.");
    Assertions.assertFalse(result);
  }

  @Test
  public void testIsValidMail8() {
    boolean result = InputModel.isValidMail("test@domain..com");
    Assertions.assertFalse(result);
  }

  @Test
  public void testIsValidMail9() {
    boolean result = InputModel.isValidMail("test@domain.c");
    Assertions.assertFalse(result);
  }

  @Test
  public void testIsValidMail10() {
    boolean result = InputModel.isValidMail("test@domain.corporate");
    Assertions.assertFalse(result);
  }

  @Test
  public void testIsValidMail11() {
    boolean result = InputModel.isValidMail("");
    Assertions.assertFalse(result);
  }

  @Test
  public void testIsValidPhoneNumber() {
    boolean result = InputModel.isValidPhoneNumber("12345678");
    Assertions.assertTrue(result);
  }

  @Test
  public void testIsValidPhoneNumber2() {
    boolean result = InputModel.isValidPhoneNumber("");
    Assertions.assertFalse(result);
  }

  @Test
  public void testIsValidPhoneNumber3() {
    boolean result = InputModel.isValidPhoneNumber("+4512345678");
    Assertions.assertTrue(result);
  }

  @Test
  public void testIsValidPhoneNumber4() {
    boolean result = InputModel.isValidPhoneNumber("004512345678");
    Assertions.assertTrue(result);
  }

  @Test
  public void testIsValidPhoneNumber5() {
    boolean result = InputModel.isValidPhoneNumber("test");
    Assertions.assertFalse(result);
  }
}

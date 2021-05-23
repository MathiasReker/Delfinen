package com.app.models;

import com.app.models.types.DistanceType;
import com.app.models.types.StyleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DisciplineModelTest {
  @Test
  public void testGetDistanceWithADistanceOfFifty() {
    int result = DistanceType.FIFTY.getMeters();

    Assertions.assertEquals(50, result);
  }

  @Test
  public void testGetDistanceWithADistanceOfHundred() {
    int result = DistanceType.HUNDRED.getMeters();

    Assertions.assertEquals(100, result);
  }

  @Test
  public void testGetDistanceWithADistanceOfTwoHundred() {
    int result = DistanceType.TWO_HUNDRED.getMeters();

    Assertions.assertEquals(200, result);
  }

  @Test
  public void testGetDistanceWithADistanceOfFourHundred() {
    int result = DistanceType.FOUR_HUNDRED.getMeters();

    Assertions.assertEquals(400, result);
  }

  @Test
  public void testGetDistanceWithADistanceOfEightHundred() {
    int result = DistanceType.EIGHT_HUNDRED.getMeters();

    Assertions.assertEquals(800, result);
  }

  @Test
  public void testGetDistanceWithADistanceOfFifteenHundred() {
    int result = DistanceType.FIFTEEN_HUNDRED.getMeters();

    Assertions.assertEquals(1500, result);
  }

  @Test
  public void testGetStyleButterfly() {
    String result = StyleType.BUTTERFLY.name();

    Assertions.assertEquals("BUTTERFLY", result);
  }

  @Test
  public void testGetStyleBackstroke() {
    String result = StyleType.BACKSTROKE.name();

    Assertions.assertEquals("BACKSTROKE", result);
  }

  @Test
  public void testGetStyleFreestyle() {
    String result = StyleType.FREESTYLE.name();

    Assertions.assertEquals("FREESTYLE", result);
  }

  @Test
  public void testGetStyleMedley() {
    String result = StyleType.MEDLEY.name();

    Assertions.assertEquals("MEDLEY", result);
  }
}

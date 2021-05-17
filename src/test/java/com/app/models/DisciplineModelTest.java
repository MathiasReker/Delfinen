package com.app.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DisciplineModelTest {

  @Test
  public void testGetDistanceWithADistanceOfFifty() {

    int result = DistanceModel.FIFTY.getMeters();

    Assertions.assertEquals(50, result);
  }

  @Test
  public void testGetDistanceWithADistanceOfHundred() {

    int result = DistanceModel.HUNDRED.getMeters();

    Assertions.assertEquals(100, result);
  }

  @Test
  public void testGetDistanceWithADistanceOfTwoHundred() {

    int result = DistanceModel.TWO_HUNDRED.getMeters();

    Assertions.assertEquals(200, result);
  }

  @Test
  public void testGetDistanceWithADistanceOfFourHundred() {

    int result = DistanceModel.FOUR_HUNDRED.getMeters();

    Assertions.assertEquals(400, result);
  }

  @Test
  public void testGetDistanceWithADistanceOfEightHundred() {

    int result = DistanceModel.EIGHT_HUNDRED.getMeters();

    Assertions.assertEquals(800, result);
  }

  @Test
  public void testGetDistanceWithADistanceOfFifteenHundred() {

    int result = DistanceModel.FIFTEEN_HUNDRED.getMeters();

    Assertions.assertEquals(1500, result);
  }

  @Test
  public void testGetStyleButterfly() {

    String result = StyleModel.BUTTERFLY.toString();

    Assertions.assertEquals("BUTTERFLY", result);
  }

  @Test
  public void testGetStyleBackstroke() {

    String result = StyleModel.BACKSTROKE.toString();

    Assertions.assertEquals("BACKSTROKE", result);
  }

  @Test
  public void testGetStyleFreestyle() {

    String result = StyleModel.FREESTYLE.toString();

    Assertions.assertEquals("FREESTYLE", result);
  }

  @Test
  public void testGetStyleMedley() {

    String result = StyleModel.MEDLEY.toString();

    Assertions.assertEquals("MEDLEY", result);
  }
}

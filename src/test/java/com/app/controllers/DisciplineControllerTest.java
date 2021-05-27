package com.app.controllers;

import com.app.models.DisciplineModel;
import com.app.models.types.DistanceType;
import com.app.models.types.GenderType;
import com.app.models.types.StyleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class DisciplineControllerTest {
  @Test
  public void testChosenDistance() {
    ArrayList<DisciplineModel> expect = new ArrayList<>();
    expect.add(new DisciplineModel(DistanceType.FIFTY, StyleType.BUTTERFLY));
    expect.add(new DisciplineModel(DistanceType.HUNDRED, StyleType.BUTTERFLY));
    expect.add(new DisciplineModel(DistanceType.TWO_HUNDRED, StyleType.BUTTERFLY));

    ArrayList<DisciplineModel> result =
        new DisciplineController().chosenDiscipline(GenderType.MALE, StyleType.BUTTERFLY);

    Assertions.assertEquals(expect.get(2).getDistance(), result.get(2).getDistance());
  }

  @Test
  public void testChosenDistanceMaleFreestyle() {
    ArrayList<DisciplineModel> expect = new ArrayList<>();
    expect.add(new DisciplineModel(DistanceType.FIFTY, StyleType.BUTTERFLY));
    expect.add(new DisciplineModel(DistanceType.HUNDRED, StyleType.BUTTERFLY));
    expect.add(new DisciplineModel(DistanceType.TWO_HUNDRED, StyleType.BUTTERFLY));
    expect.add(new DisciplineModel(DistanceType.FOUR_HUNDRED, StyleType.BUTTERFLY));
    expect.add(new DisciplineModel(DistanceType.FIFTEEN_HUNDRED, StyleType.BUTTERFLY));

    ArrayList<DisciplineModel> result =
        new DisciplineController().chosenDiscipline(GenderType.MALE, StyleType.FREESTYLE);

    Assertions.assertEquals(expect.get(4).getDistance(), result.get(4).getDistance());
  }

  @Test
  public void testChosenDistanceWomanFreestyle() {
    ArrayList<DisciplineModel> expect = new ArrayList<>();
    expect.add(new DisciplineModel(DistanceType.FIFTY, StyleType.BUTTERFLY));
    expect.add(new DisciplineModel(DistanceType.HUNDRED, StyleType.BUTTERFLY));
    expect.add(new DisciplineModel(DistanceType.TWO_HUNDRED, StyleType.BUTTERFLY));
    expect.add(new DisciplineModel(DistanceType.FOUR_HUNDRED, StyleType.BUTTERFLY));
    expect.add(new DisciplineModel(DistanceType.EIGHT_HUNDRED, StyleType.BUTTERFLY));

    ArrayList<DisciplineModel> result =
        new DisciplineController().chosenDiscipline(GenderType.FEMALE, StyleType.FREESTYLE);

    Assertions.assertEquals(expect.get(4).getDistance(), result.get(4).getDistance());
  }

  @Test
  public void testChosenDistanceWomanFreestyleFail() {
    ArrayList<DisciplineModel> expect =
        new DisciplineController().chosenDiscipline(GenderType.MALE, StyleType.FREESTYLE);

    ArrayList<DisciplineModel> result =
        new DisciplineController().chosenDiscipline(GenderType.FEMALE, StyleType.FREESTYLE);

    Assertions.assertNotEquals(expect.get(4).getDistance(), result.get(4).getDistance());
  }
}

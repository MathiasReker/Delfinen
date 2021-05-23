package com.app.controllers;

import com.app.models.DisciplineModel;
import com.app.models.types.DistanceType;
import com.app.models.types.GenderType;
import com.app.models.types.StyleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class DisciplinesControllerTest {

  @Test
  public void testChosenDistance() {

    ArrayList<DisciplineModel> expect = new ArrayList<>();
    expect.add(new DisciplineModel(DistanceType.FIFTY.getMeters(), StyleType.BUTTERFLY.name()));
    expect.add(new DisciplineModel(DistanceType.HUNDRED.getMeters(), StyleType.BUTTERFLY.name()));
    expect.add(
        new DisciplineModel(DistanceType.TWO_HUNDRED.getMeters(), StyleType.BUTTERFLY.name()));

    ArrayList<DisciplineModel> result =
        new DisciplinesController()
            .chosenDiscipline(GenderType.MALE.name(), StyleType.BUTTERFLY.name());

    Assertions.assertEquals(expect.get(2).getDistance(), result.get(2).getDistance());
  }

  @Test
  public void testChosenDistanceMaleFreestyle() {

    ArrayList<DisciplineModel> expect = new ArrayList<>();
    expect.add(new DisciplineModel(DistanceType.FIFTY.getMeters(), StyleType.BUTTERFLY.name()));
    expect.add(new DisciplineModel(DistanceType.HUNDRED.getMeters(), StyleType.BUTTERFLY.name()));
    expect.add(
        new DisciplineModel(DistanceType.TWO_HUNDRED.getMeters(), StyleType.BUTTERFLY.name()));
    expect.add(
        new DisciplineModel(DistanceType.FOUR_HUNDRED.getMeters(), StyleType.BUTTERFLY.name()));
    expect.add(
        new DisciplineModel(DistanceType.FIFTEEN_HUNDRED.getMeters(), StyleType.BUTTERFLY.name()));

    ArrayList<DisciplineModel> result =
        new DisciplinesController()
            .chosenDiscipline(GenderType.MALE.name(), StyleType.FREESTYLE.name());

    Assertions.assertEquals(expect.get(4).getDistance(), result.get(4).getDistance());
  }

  @Test
  public void testChosenDistanceWomanFreestyle() {

    ArrayList<DisciplineModel> expect = new ArrayList<>();
    expect.add(new DisciplineModel(DistanceType.FIFTY.getMeters(), StyleType.BUTTERFLY.name()));
    expect.add(new DisciplineModel(DistanceType.HUNDRED.getMeters(), StyleType.BUTTERFLY.name()));
    expect.add(
        new DisciplineModel(DistanceType.TWO_HUNDRED.getMeters(), StyleType.BUTTERFLY.name()));
    expect.add(
        new DisciplineModel(DistanceType.FOUR_HUNDRED.getMeters(), StyleType.BUTTERFLY.name()));
    expect.add(
        new DisciplineModel(DistanceType.EIGHT_HUNDRED.getMeters(), StyleType.BUTTERFLY.name()));

    ArrayList<DisciplineModel> result =
        new DisciplinesController()
            .chosenDiscipline(GenderType.FEMALE.name(), StyleType.FREESTYLE.name());

    Assertions.assertEquals(expect.get(4).getDistance(), result.get(4).getDistance());
  }

  @Test
  public void testChosenDistanceWomanFreestyleFail() {

    ArrayList<DisciplineModel> expect =
        new DisciplinesController()
            .chosenDiscipline(GenderType.MALE.name(), StyleType.FREESTYLE.name());

    ArrayList<DisciplineModel> result =
        new DisciplinesController()
            .chosenDiscipline(GenderType.FEMALE.name(), StyleType.FREESTYLE.name());

    Assertions.assertNotEquals(expect.get(4).getDistance(), result.get(4).getDistance());
  }
}

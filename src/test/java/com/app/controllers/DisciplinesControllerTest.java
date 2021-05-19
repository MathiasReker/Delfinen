package com.app.controllers;

import com.app.models.DisciplineModel;
import com.app.models.DistanceModel;
import com.app.models.StyleModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class DisciplinesControllerTest {

  @Test
  public void testChosenDistance() {

    ArrayList<DisciplineModel> expect = new ArrayList<>();
    expect.add(new DisciplineModel(DistanceModel.FIFTY.getMeters(), StyleModel.BUTTERFLY.name()));
    expect.add(new DisciplineModel(DistanceModel.HUNDRED.getMeters(), StyleModel.BUTTERFLY.name()));
    expect.add(
        new DisciplineModel(DistanceModel.TWO_HUNDRED.getMeters(), StyleModel.BUTTERFLY.name()));

    ArrayList<DisciplineModel> result =
        new DisciplinesController().chosenDiscipline(1, StyleModel.BUTTERFLY.toString());

    Assertions.assertEquals(expect.get(2).getDistance(), result.get(2).getDistance());
  }

  @Test
  public void testChosenDistanceMaleFreestyle() {

    ArrayList<DisciplineModel> expect = new ArrayList<>();
    expect.add(new DisciplineModel(DistanceModel.FIFTY.getMeters(), StyleModel.BUTTERFLY.name()));
    expect.add(new DisciplineModel(DistanceModel.HUNDRED.getMeters(), StyleModel.BUTTERFLY.name()));
    expect.add(
        new DisciplineModel(DistanceModel.TWO_HUNDRED.getMeters(), StyleModel.BUTTERFLY.name()));
    expect.add(
        new DisciplineModel(DistanceModel.FOUR_HUNDRED.getMeters(), StyleModel.BUTTERFLY.name()));
    expect.add(
        new DisciplineModel(
            DistanceModel.FIFTEEN_HUNDRED.getMeters(), StyleModel.BUTTERFLY.name()));

    ArrayList<DisciplineModel> result =
        new DisciplinesController().chosenDiscipline(1, StyleModel.FREESTYLE.toString());

    Assertions.assertEquals(expect.get(4).getDistance(), result.get(4).getDistance());
  }

  @Test
  public void testChosenDistanceWomanFreestyle() {

    ArrayList<DisciplineModel> expect = new ArrayList<>();
    expect.add(new DisciplineModel(DistanceModel.FIFTY.getMeters(), StyleModel.BUTTERFLY.name()));
    expect.add(new DisciplineModel(DistanceModel.HUNDRED.getMeters(), StyleModel.BUTTERFLY.name()));
    expect.add(
        new DisciplineModel(DistanceModel.TWO_HUNDRED.getMeters(), StyleModel.BUTTERFLY.name()));
    expect.add(
        new DisciplineModel(DistanceModel.FOUR_HUNDRED.getMeters(), StyleModel.BUTTERFLY.name()));
    expect.add(
        new DisciplineModel(DistanceModel.EIGHT_HUNDRED.getMeters(), StyleModel.BUTTERFLY.name()));

    ArrayList<DisciplineModel> result =
        new DisciplinesController().chosenDiscipline(2, StyleModel.FREESTYLE.toString());

    Assertions.assertEquals(expect.get(4).getDistance(), result.get(4).getDistance());
  }

  @Test
  public void testChosenDistanceWomanFreestyleFail() {

    ArrayList<DisciplineModel> expect =
        new DisciplinesController().chosenDiscipline(1, StyleModel.FREESTYLE.toString());

    ArrayList<DisciplineModel> result =
        new DisciplinesController().chosenDiscipline(2, StyleModel.FREESTYLE.toString());

    Assertions.assertNotEquals(expect.get(4).getDistance(), result.get(4).getDistance());
  }
}

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
    expect.add(new DisciplineModel(DistanceModel.FIFTY.getMeters(), StyleModel.BUTTERFLY.toString()));
    expect.add(new DisciplineModel(DistanceModel.HUNDRED.getMeters(), StyleModel.BUTTERFLY.toString()));
    expect.add(new DisciplineModel(DistanceModel.TWO_HUNDRED.getMeters(), StyleModel.BUTTERFLY.toString()));

    ArrayList<DisciplineModel> result = new DisciplinesController().chosenDiscipline(1,StyleModel.BUTTERFLY.toString());

    Assertions.assertEquals(expect, result);

  }

  @Test
  public void testChosenDistanceMaleFreestyle() {

    ArrayList<DisciplineModel> expect = new ArrayList<>();
    expect.add(new DisciplineModel(DistanceModel.FIFTY.getMeters(), StyleModel.FREESTYLE.toString()));
    expect.add(new DisciplineModel(DistanceModel.HUNDRED.getMeters(), StyleModel.FREESTYLE.toString()));
    expect.add(new DisciplineModel(DistanceModel.TWO_HUNDRED.getMeters(), StyleModel.FREESTYLE.toString()));
    expect.add(new DisciplineModel(DistanceModel.FOUR_HUNDRED.getMeters(), StyleModel.FREESTYLE.toString()));
    expect.add(new DisciplineModel(DistanceModel.FIFTEEN_HUNDRED.getMeters(), StyleModel.FREESTYLE.toString()));

    ArrayList<DisciplineModel> result = new DisciplinesController().chosenDiscipline(1,StyleModel.FREESTYLE.toString());

    Assertions.assertEquals(expect, result);

  }
}
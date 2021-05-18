package com.app.controllers;

import com.app.models.DisciplineModel;
import com.app.models.DistanceModel;
import com.app.models.StyleModel;

import java.util.ArrayList;

public class DisciplinesController {


  /**
   * Output a list with the disciplines for the specific swim style.
   *
   * @param gender gets the gender, 1 is boy, 2 is girl, 3 is other
   * @param style  that define the distance.
   * @return a list og disciplines.
   */
  public ArrayList<DisciplineModel> chosenDiscipline(int gender, String style) {

    if (!styleExist(style)) {
      throw new IllegalArgumentException("No such style");
    }

    ArrayList<DisciplineModel> Distances = new ArrayList<>();

    Distances.add(new DisciplineModel(DistanceModel.FIFTY.getMeters(), style));
    Distances.add(new DisciplineModel(DistanceModel.HUNDRED.getMeters(), style));
    Distances.add(new DisciplineModel(DistanceModel.TWO_HUNDRED.getMeters(), style));

    if (style.equals(StyleModel.MEDLEY.toString())) {
      Distances.add(new DisciplineModel(DistanceModel.FOUR_HUNDRED.getMeters(), style));
    }

    if (style.equals(StyleModel.FREESTYLE.toString())) {
      Distances.add(new DisciplineModel(DistanceModel.FOUR_HUNDRED.getMeters(), style));
      if (gender == 1) {
        Distances.add(new DisciplineModel(DistanceModel.FIFTEEN_HUNDRED.getMeters(), style));
      } else if (gender == 2) {
        Distances.add(new DisciplineModel(DistanceModel.EIGHT_HUNDRED.getMeters(), style));
      } else {
        Distances.add(new DisciplineModel(DistanceModel.EIGHT_HUNDRED.getMeters(), style));
        Distances.add(new DisciplineModel(DistanceModel.FIFTEEN_HUNDRED.getMeters(), style));
      }
    }

    return Distances;
  }

  private boolean styleExist(String style) {
    for (StyleModel sm : StyleModel.values()) {
      if (sm.name().equals(style)) {
        return true;
      }
    }
    return false;
  }

}

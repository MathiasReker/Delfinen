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
   * @param style that define the distance.
   * @return a list og disciplines.
   */
  public ArrayList<DisciplineModel> chosenDiscipline(int gender, String style) {

    if (!styleExist(style)) {
      throw new IllegalArgumentException("No such style");
    }
    ArrayList<DistanceModel> distances = new ArrayList<>();

    distances.add(DistanceModel.FIFTY);
    distances.add(DistanceModel.HUNDRED);
    distances.add(DistanceModel.TWO_HUNDRED);

    if (style.equals(StyleModel.MEDLEY.name())) {
      distances.add(DistanceModel.FOUR_HUNDRED);
    }

    if (style.equals(StyleModel.FREESTYLE.name())) {
      distances.add(DistanceModel.FOUR_HUNDRED);
      if (1 == gender) {
        distances.add(DistanceModel.FIFTEEN_HUNDRED);
      } else if (2 == gender) {
        distances.add(DistanceModel.EIGHT_HUNDRED);
      } else {
        distances.add(DistanceModel.EIGHT_HUNDRED);
        distances.add(DistanceModel.FIFTEEN_HUNDRED);
      }
    }

    ArrayList<DisciplineModel> result = new ArrayList<>();

    for (DistanceModel d : distances) {
      result.add(new DisciplineModel(d.getMeters(), style));
    }

    return result;
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

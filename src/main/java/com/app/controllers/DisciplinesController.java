package com.app.controllers;

import com.app.models.DisciplineModel;
import com.app.models.types.DistanceType;
import com.app.models.types.GenderType;
import com.app.models.types.StyleType;

import java.util.ArrayList;

public class DisciplinesController {

  private ArrayList<DisciplineModel> disciplines = new ArrayList<>();

  public DisciplinesController() {
    setDisciplines(disciplines);
  }

  public ArrayList<DisciplineModel> getDisciplines() {
    return disciplines; // TODO: never used
  }

  public void setDisciplines(ArrayList<DisciplineModel> disciplines) {
    this.disciplines = disciplines;
  }

  /**
   * Output a list with the disciplines for the specific swim style.
   *
   * @param gender gets the gender, 1 is boy, 2 is girl, 3 is other
   * @param style that define the distance.
   * @return a list og disciplines.
   */
  public ArrayList<DisciplineModel> chosenDiscipline(String gender, String style) {
    if (!styleExist(style)) {
      throw new IllegalArgumentException("No such style");
    }
    ArrayList<DistanceType> distances = new ArrayList<>();

    distances.add(DistanceType.FIFTY);
    distances.add(DistanceType.HUNDRED);
    distances.add(DistanceType.TWO_HUNDRED);

    if (style.equals(StyleType.MEDLEY.name())) {
      distances.add(DistanceType.FOUR_HUNDRED);
    }

    if (style.equals(StyleType.FREESTYLE.name())) {
      distances.add(DistanceType.FOUR_HUNDRED);
      if (gender.equals(GenderType.MALE.name())) {
        distances.add(DistanceType.FIFTEEN_HUNDRED);
      } else if (gender.equals(GenderType.FEMALE.name())) {
        distances.add(DistanceType.EIGHT_HUNDRED);
      } else {
        distances.add(DistanceType.EIGHT_HUNDRED);
        distances.add(DistanceType.FIFTEEN_HUNDRED);
      }
    }

    ArrayList<DisciplineModel> result = new ArrayList<>();

    for (DistanceType d : distances) {
      result.add(new DisciplineModel(d.getMeters(), style));
    }

    return result;
  }

  private boolean styleExist(String style) {
    for (StyleType sm : StyleType.values()) {
      if (sm.name().equals(style)) {
        return true;
      }
    }

    return false;
  }
}

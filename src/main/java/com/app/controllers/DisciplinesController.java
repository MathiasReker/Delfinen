package com.app.controllers;

import com.app.models.DisciplineModel;
import com.app.models.DistanceModel;
import com.app.models.GenderModel;
import com.app.models.StyleModel;

import java.util.ArrayList;

public class DisciplinesController {

  private ArrayList<DisciplineModel> disciplines = new ArrayList<>();

  public DisciplinesController() {
    setDisciplines(disciplines);
  }

  public ArrayList<DisciplineModel> getDisciplines() {
    return disciplines;
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
    ArrayList<DistanceModel> distances = new ArrayList<>();

    distances.add(DistanceModel.FIFTY);
    distances.add(DistanceModel.HUNDRED);
    distances.add(DistanceModel.TWO_HUNDRED);

    if (style.equals(StyleModel.MEDLEY.name())) {
      distances.add(DistanceModel.FOUR_HUNDRED);
    }

    if (style.equals(StyleModel.FREESTYLE.name())) {
      distances.add(DistanceModel.FOUR_HUNDRED);
      if (gender.equals(GenderModel.MALE.name())) {
        distances.add(DistanceModel.FIFTEEN_HUNDRED);
      } else if (gender.equals(GenderModel.FEMALE.name())) {
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

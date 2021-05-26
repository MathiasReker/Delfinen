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
  public ArrayList<DisciplineModel> chosenDiscipline(GenderType gender, StyleType style) {
    ArrayList<DistanceType> distances = new ArrayList<>();

    distances.add(DistanceType.FIFTY);
    distances.add(DistanceType.HUNDRED);
    distances.add(DistanceType.TWO_HUNDRED);

    if (style.equals(StyleType.MEDLEY)) {
      distances.add(DistanceType.FOUR_HUNDRED);
    }

    if (style.equals(StyleType.FREESTYLE)) {
      distances.add(DistanceType.FOUR_HUNDRED);
      if (gender.equals(GenderType.MALE)) {
        distances.add(DistanceType.FIFTEEN_HUNDRED);
      } else if (gender.equals(GenderType.FEMALE)) {
        distances.add(DistanceType.EIGHT_HUNDRED);
      } else {
        distances.add(DistanceType.EIGHT_HUNDRED);
        distances.add(DistanceType.FIFTEEN_HUNDRED);
      }
    }

    ArrayList<DisciplineModel> result = new ArrayList<>();

    for (DistanceType d : distances) {
      result.add(new DisciplineModel(d, style));
    }

    return result;
  }

  /** @return a String Array of converted styles */
  public String[] styleToArray() {
    String[] result = new String[StyleType.values().length];

    for (int i = 0; i < result.length; i++) {
      result[i] = StyleType.values()[i].name();
    }

    return result;
  }

  /**
   * Creates an array of the distances available based on swim style and gender
   *
   * @param style the style we wish to filter on
   * @param gender the gender we wish to filter on
   * @return a String array with filtered distances
   */
  public String[] distanceToArray(StyleType style, GenderType gender) {
    ArrayList<DisciplineModel> disciplineModels = chosenDiscipline(gender, style);
    String[] result = new String[disciplineModels.size()];

    for (int i = 0; i < result.length; i++) {
      result[i] = String.valueOf(disciplineModels.get(i).getDistance());
    }

    return result;
  }
}

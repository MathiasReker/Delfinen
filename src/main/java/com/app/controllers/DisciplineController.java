package com.app.controllers;

import com.app.models.DisciplineModel;
import com.app.models.types.DistanceType;
import com.app.models.types.GenderType;
import com.app.models.types.StyleType;
import com.app.views.InputView;

import java.util.ArrayList;

public class DisciplineController {

  private final InputView VIEW = new InputView();

  private ArrayList<DisciplineModel> disciplines = new ArrayList<>();

  public DisciplineController() {
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
  // Author: Andreas
  public boolean lookupDiscipline(
      ArrayList<DisciplineModel> disciplines, DisciplineModel discipline) {
    for (DisciplineModel disciplineModel : disciplines) {
      return disciplineModel.getDistance().equals(discipline.getDistance())
          && disciplineModel.getStyle().equals(discipline.getStyle());
    }
    return false;
  }
  // Author: Andreas
  public DisciplineModel getDiscipline(
      ArrayList<DisciplineModel> disciplines, DisciplineModel discipline) {
    for (DisciplineModel disciplineModel : disciplines) {
      if (disciplineModel.getDistance().equals(discipline.getDistance())
          && disciplineModel.getStyle().equals(discipline.getStyle())) {
        return disciplineModel;
      }
    }
    return null;
  }

  // Author: Mohammed, Andreas
  public DisciplineModel getDisciplineModelStyleAndDistance(GenderType gender) {

    VIEW.displayOptions(styleToArray());
    int styleChoice = InputController.validateOptionRange(styleToArray().length);

    String[] distances = distanceToArray(StyleType.values()[styleChoice - 1], gender);
    VIEW.displayOptions(distances);
    int distanceChoice = InputController.validateOptionRange(distances.length);

    StyleType style = StyleType.values()[styleChoice - 1];
    DistanceType distance = DistanceType.values()[distanceChoice - 1];

    ArrayList<DisciplineModel> temp = chosenDiscipline(gender, style);
    for (DisciplineModel discipline : temp) {
      if (discipline.getDistance().equals(distance)) {
        return discipline;
      }
    }
    return null;
  }

  // Author: Andreas
  public String[] getDisciplineDescriptions(ArrayList<DisciplineModel> disciplines) {
    String[] result = new String[disciplines.size()];
    for (int i = 0; i < disciplines.size(); i++) {
      result[i] =
          disciplines.get(i).getDistance().getMeters()
              + "m "
              + disciplines.get(i).getStyle().name();
    }
    return result;
  }
}

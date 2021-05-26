package com.app.controllers;

import com.app.models.CompetitionModel;
import com.app.models.DisciplineModel;
import com.app.models.MemberModel;
import com.app.models.ResultModel;
import com.app.models.types.DistanceType;
import com.app.models.types.GenderType;
import com.app.models.types.StyleType;
import com.app.views.ResultView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ResultController {

  private final ResultView VIEW = new ResultView();
  private final CompetitionController COMPETITION_CONTROLLER = new CompetitionController();


  /**
   * Adds a result time to a competition
   *
   * @param member Member that we want to add a result to
   * @param competition The competition that we want to add a result to
   */
  public ResultModel addResultTime(MemberModel member, CompetitionModel competition) {
    VIEW.displayOptions(styleToArray());
    int styleChoice = InputController.validateOptionRange(styleToArray().length);

    String[] distances = distanceToArray(StyleType.values()[styleChoice - 1], member.getGender());
    VIEW.displayOptions(distances);
    int distanceChoice = InputController.validateOptionRange(distances.length);

    VIEW.printInline("Result time [mm:ss:SS]: ");
    LocalTime time =
        LocalTime.parse(
            "00:" + InputController.validateCompetitionResultTime(),
            DateTimeFormatter.ofPattern("HH:mm:ss:SS"));

    VIEW.printInline("Placement: ");
    String placement = InputController.validatePlacement();

    DisciplineModel disciplineModel =
        new DisciplineModel(
            DistanceType.values()[distanceChoice - 1], StyleType.values()[styleChoice - 1]);
    return  new ResultModel(member, time, disciplineModel, competition, placement);
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
    DisciplinesController disciplinesController = new DisciplinesController();
    ArrayList<DisciplineModel> disciplineModels =
        disciplinesController.chosenDiscipline(gender, style);
    String[] result = new String[disciplineModels.size()];

    for (int i = 0; i < result.length; i++) {
      result[i] = String.valueOf(disciplineModels.get(i).getDistance());
    }

    return result;
  }

}

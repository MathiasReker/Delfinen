package com.app.controllers;

import com.app.models.CompetitionModel;
import com.app.models.DisciplineModel;
import com.app.models.MemberModel;
import com.app.models.ResultModel;
import com.app.models.types.DistanceType;
import com.app.models.types.StyleType;
import com.app.views.ResultView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ResultController {

  private final ResultView VIEW = new ResultView();
  private final DisciplinesController DISC_CONTROLLER = new DisciplinesController();


  /**
   * Adds a result time to a competition
   *
   * @param member Member that we want to add a result to
   * @param competition The competition that we want to add a result to
   */
  public ResultModel addResultTime(MemberModel member, CompetitionModel competition) {
    VIEW.displayOptions(DISC_CONTROLLER.styleToArray());
    int styleChoice = InputController.validateOptionRange(DISC_CONTROLLER.styleToArray().length);

    String[] distances = DISC_CONTROLLER.distanceToArray(StyleType.values()[styleChoice - 1], member.getGender());
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
}

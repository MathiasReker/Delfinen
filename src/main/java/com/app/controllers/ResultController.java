package com.app.controllers;

import com.app.models.SwimEventModel;
import com.app.models.DisciplineModel;
import com.app.models.MemberModel;
import com.app.models.ResultModel;
import com.app.views.ResultView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ResultController {

  private final ResultView VIEW = new ResultView();
  private final DisciplineController DISC_CONTROLLER = new DisciplineController();

  /**
   * Adds a result time to a competition
   *
   * @param member Member that we want to add a result to
   * @param competition The competition that we want to add a result to
   */
  public ResultModel addResultTime(MemberModel member, SwimEventModel competition) {
    DisciplineModel disciplineModel =
        DISC_CONTROLLER.getDisciplineModelStyleAndDistance(member.getGender());

    VIEW.printInline("Result time [mm:ss:SS]: ");
    LocalTime time =
        LocalTime.parse(
            "00:" + InputController.validateCompetitionResultTime(),
            DateTimeFormatter.ofPattern("HH:mm:ss:SS"));

    String placement = "0";
    if (!competition.isPractice()) {
      VIEW.printInline("Placement: ");
      placement = InputController.validatePlacement();
    }

    return new ResultModel(member, time, disciplineModel, competition, placement);
  }
}

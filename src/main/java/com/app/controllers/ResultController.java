package com.app.controllers;

import com.app.models.DisciplineModel;
import com.app.models.MemberModel;
import com.app.models.ResultModel;
import com.app.models.SwimEventModel;
import com.app.views.ResultView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ResultController {

  private final ResultView VIEW = new ResultView();
  private final DisciplineController DISC_CONTROLLER = new DisciplineController();

  /**
   * Create/returns a new ResultModel.
   *
   * @param member MemberModel
   * @param swimEvent SwimEventModel
   * @return ResultModel
   * @auther Mohamad, Jackie
   */
  ResultModel addResultTime(MemberModel member, SwimEventModel swimEvent) {
    DisciplineModel disciplineModel =
        DISC_CONTROLLER.getDisciplineModelStyleAndDistance(member.getGender());

    VIEW.printInline("Result time [mm:ss:SS]: ");
    LocalTime time =
        LocalTime.parse(
            "00:" + InputController.validateResultTime(),
            DateTimeFormatter.ofPattern("HH:mm:ss:SS"));

    String placement;
    if (!swimEvent.isPractice()) {
      VIEW.printInline("Placement: ");
      placement = InputController.validatePlacement();
    } else {
      placement = "Practice";
    }

    return new ResultModel(member, time, disciplineModel, swimEvent, placement);
  }
}

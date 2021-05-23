package com.app.controllers;

import com.app.models.*;
import com.app.views.CompetitionView;

import java.util.ArrayList;
import java.util.Collections;

public class LeaderboardController {
  private final CompetitionView VIEW = new CompetitionView();
  private final CompetitionController COMPETITION_CONTROLLER = new CompetitionController();
  private final ArrayList<CompetitionModel> ALL_COMPETITIONS =
      COMPETITION_CONTROLLER.getCompetitions();

  /**
   * find top swimmers in all competitions.
   *
   * @param style to check.
   * @param distance of the style.
   * @return an array of the amount the fastest swimmers in a given discipline.
   */
  private ResultModel[] findTop5(String style, int distance) {
    ResultModel[] result = new ResultModel[5];
    ArrayList<ResultModel> allResults = findDiscipline(style, distance);

    Collections.sort(allResults);

    for (int i = 0; i < result.length; i++) {
      result[i] = allResults.get(i);
    }

    return result;
  }

  private ArrayList<ResultModel> findDiscipline(String style, int distance) {
    ArrayList<ResultModel> result = new ArrayList<>();

    for (CompetitionModel cm : ALL_COMPETITIONS) {

      for (int j = 0; j < cm.getResult().size(); j++) {
        if (cm.getResult().get(j).getDiscipline().getStyle().equals(style)
            && cm.getResult().get(j).getDiscipline().getDistance() == distance) {
          result.add(cm.getResult().get(j));
        }
      }
    }

    return result;
  }

  public void displayTop5Results() {
    if (!ALL_COMPETITIONS.isEmpty()) {
      VIEW.displayOptions(COMPETITION_CONTROLLER.styleToArray());

      int styleInput =
          InputController.validateOptionRange(COMPETITION_CONTROLLER.styleToArray().length);
      String style = StyleModel.values()[styleInput - 1].name(); // TODO: sure about the -1?
      VIEW.displayOptions(COMPETITION_CONTROLLER.distanceToArray(style, GenderModel.OTHER));

      int distanceInput =
          InputController.validateOptionRange(
              COMPETITION_CONTROLLER.distanceToArray(style, GenderModel.OTHER).length);
      int distance =
          DistanceModel.values()[distanceInput - 1].getMeters(); // TODO: sure about the -1?

      ResultModel[] top5 = findTop5(style, distance);
      for (ResultModel s : top5) {
        VIEW.print(String.valueOf(s));
        // TODO: Top5 was not printed. This is is quick fix. However, this is not tested.
      }
    }
  }
}

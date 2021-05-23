package com.app.controllers;

import com.app.models.CompetitionModel;
import com.app.models.ResultModel;
import com.app.models.types.DistanceType;
import com.app.models.types.GenderType;
import com.app.models.types.StyleType;
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
    ResultModel[] result =
        new ResultModel[5]; // TODO: Refactor to arraylist, sort and limit to 5 at the display.
    // TODO: Must work with less than 5 result. :-)
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
      String style = StyleType.values()[styleInput - 1].name();
      VIEW.displayOptions(COMPETITION_CONTROLLER.distanceToArray(style, GenderType.OTHER));

      int distanceInput =
          InputController.validateOptionRange(
              COMPETITION_CONTROLLER.distanceToArray(style, GenderType.OTHER).length);
      int distance = DistanceType.values()[distanceInput - 1].getMeters();

      ResultModel[] top5 = findTop5(style, distance);
      for (int i = 0, top5Length = top5.length; i < top5Length; i++) {
        VIEW.print(
            String.format(
                "%d: %s",
                i + 1, top5[i].getMember())); // TODO: Add rest of the details and move to view.
      }
    }
  }
}

package com.app.controllers;

import com.app.models.*;
import com.app.views.CompetitionView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

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

  public void displayTopResults(Scanner in) {
    if (!ALL_COMPETITIONS.isEmpty()) {
      String style;
      int distance;

      VIEW.displayOptions(COMPETITION_CONTROLLER.styleToArray());
      int input = in.nextInt();
      while (!ValidateModel.isValidRange(input, 1, COMPETITION_CONTROLLER.styleToArray().length)) {
        in.next(); // TODO: Validate
        input = in.nextInt();
      }
      style = StyleModel.values()[input - 1].name();

      VIEW.displayOptions(COMPETITION_CONTROLLER.distanceToArray(style, GenderModel.OTHER));
      input = in.nextInt();
      while (!ValidateModel.isValidRange(
          input, 1, COMPETITION_CONTROLLER.distanceToArray(style, GenderModel.OTHER).length)) {
        in.next(); // TODO: Validate
        input = in.nextInt();
      }
      distance = DistanceModel.values()[input - 1].getMeters();

      findTop5(style, distance); // TODO: Output the values. Right not nothing is displayed.
    }
  }
}

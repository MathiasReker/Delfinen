package com.app.controllers;

import com.app.models.CompetitionModel;
import com.app.models.MemberModel;
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
  private ArrayList<ResultModel> findTop(StyleType style, DistanceType distance, int amount) {
    ArrayList<ResultModel> allResults = findDiscipline(style, distance);
    if (allResults.isEmpty()) {
      return allResults; // Changed from null
    }

    ArrayList<ResultModel> result = new ArrayList<>();
    Collections.sort(allResults);

    for (int i = 0; i < amount; i++) {
      if (!memberExist(result, allResults.get(i).getMember())) {
        result.add(allResults.get(i));
      } else {
        if (amount < allResults.size()) {
          amount++;
        }
      }
    }

    return result;
  }

  private boolean memberExist(ArrayList<ResultModel> finals, MemberModel member) {

    if (finals.isEmpty()) {
      return false;
    }
    for (ResultModel aFinal : finals) {
      if (aFinal.getMember().getId().equals(member.getId())) {
        return true;
      }
    }

    return false;
  }

  private ArrayList<ResultModel> findDiscipline(StyleType style, DistanceType distance) {
    ArrayList<ResultModel> result = new ArrayList<>();

    for (CompetitionModel cm : ALL_COMPETITIONS) {

      for (int j = 0; j < cm.getResult().size(); j++) {
        if (cm.getResult().get(j).getDiscipline().getStyle().equals(style)
            && cm.getResult().get(j).getDiscipline().getDistance().equals(distance)) {
          result.add(cm.getResult().get(j));
        }
      }
    }

    return result;
  }

  private String[][] arrayWithResultToDisplay(ArrayList<ResultModel> resultTimes) {
    String[][] result = new String[resultTimes.size()][4];

    for (int i = 0; i < resultTimes.size(); i++) {
      ResultModel resultModel = resultTimes.get(i);

      String competitionName = resultModel.getCompetition().getName();
      String placement = resultModel.getPlacement();
      String name = resultModel.getMember().getName();
      String completionTime = resultModel.getResultTime().toString();

      result[i] = new String[] {competitionName, placement, name, completionTime};
    }

    return result;
  }

  public void displayTop5Results() {
    if (!ALL_COMPETITIONS.isEmpty()) {
      VIEW.displayOptions(COMPETITION_CONTROLLER.styleToArray());

      int styleInput =
          InputController.validateOptionRange(COMPETITION_CONTROLLER.styleToArray().length);
      StyleType style = StyleType.values()[styleInput - 1];
      VIEW.displayOptions(COMPETITION_CONTROLLER.distanceToArray(style, GenderType.OTHER));

      int distanceInput =
          InputController.validateOptionRange(
              COMPETITION_CONTROLLER.distanceToArray(style, GenderType.OTHER).length);
      DistanceType distance = DistanceType.values()[distanceInput - 1];

      ArrayList<ResultModel> top5 = findTop(style, distance, 5);
      if (top5.isEmpty()) {
        VIEW.print("No results for " + distance.getMeters() + " m " + style + ".");
      } else {
        VIEW.displayTopResults(arrayWithResultToDisplay(top5));
      }
    }
  }
}

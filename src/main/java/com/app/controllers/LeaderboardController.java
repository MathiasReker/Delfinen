package com.app.controllers;

import com.app.models.ResultModel;
import com.app.models.SwimEventModel;
import com.app.models.types.AgeGroupType;
import com.app.models.types.DistanceType;
import com.app.models.types.GenderType;
import com.app.models.types.StyleType;
import com.app.views.SwimEventView;

import java.util.ArrayList;
import java.util.Collections;

public class LeaderboardController {
  private final SwimEventView VIEW = new SwimEventView();
  private final SwimEventController COMPETITION_CONTROLLER = new SwimEventController();
  private final DisciplineController DISC_CONTROLLER = new DisciplineController();
  private final ArrayList<SwimEventModel> ALL_COMPETITIONS =
      COMPETITION_CONTROLLER.getSwimEventModels();
  private final ResultController RESULT_CONTROLLER = new ResultController();

  /**
   * Returns top n results, where n is amount.
   *
   * @param style StyleType.
   * @param distance DistanceType
   * @param amount int
   * @param genderType GenderType
   * @param ageGroupType AgeGroupType
   * @return ArrayList<ResultModel>
   * @auther Jackie
   */
  private ArrayList<ResultModel> findTop(
      StyleType style,
      DistanceType distance,
      int amount,
      GenderType genderType,
      AgeGroupType ageGroupType) {
    ArrayList<ResultModel> allResults = findDiscipline(style, distance);

    if (allResults.isEmpty()) {
      return allResults;
    }

    ArrayList<ResultModel> result = new ArrayList<>();
    Collections.sort(allResults);
    if (amount > allResults.size()) {
      amount = allResults.size();
    }

    for (int i = 0; i < amount; i++) {
      if (!RESULT_CONTROLLER.memberInResult(result, allResults.get(i).getMember())
          && allResults.get(i).getMember().getGender() == genderType
          && allResults.get(i).getMember().getAgeGroup() == ageGroupType
          && allResults.get(i).getMember().isCompetitive()) {
        result.add(allResults.get(i));
      } else {
        if (amount < allResults.size()) {
          amount++;
        }
      }
    }

    return result;
  }

  /**
   * Returns discipline.
   *
   * @param style StyleType
   * @param distance DistanceType
   * @return ArrayList<ResultModel>
   * @auther Jackie
   */
  private ArrayList<ResultModel> findDiscipline(StyleType style, DistanceType distance) {
    ArrayList<ResultModel> result = new ArrayList<>();

    for (SwimEventModel cm : ALL_COMPETITIONS) {
      for (int j = 0; j < cm.getResult().size(); j++) {
        if (cm.getResult().get(j).getDiscipline().getStyle().equals(style)
            && cm.getResult().get(j).getDiscipline().getDistance().equals(distance)) {
          result.add(cm.getResult().get(j));
        }
      }
    }

    return result;
  }

  /**
   * Display top n results.
   *
   * @param amount int
   * @auther Jackie, Mohamad
   */
  public void displayTopResults(int amount) {
    if (!ALL_COMPETITIONS.isEmpty()) {
      VIEW.displayOptions(DISC_CONTROLLER.styleToArray());
      int styleInput = InputController.validateOptionRange(DISC_CONTROLLER.styleToArray().length);
      StyleType style = StyleType.values()[styleInput - 1];

      VIEW.displayOptions(DISC_CONTROLLER.distanceToArray(style, GenderType.OTHER));
      int distanceInput =
          InputController.validateOptionRange(
              DISC_CONTROLLER.distanceToArray(style, GenderType.OTHER).length);
      DistanceType distance = DistanceType.values()[distanceInput - 1];

      VIEW.displayOptions(ageGroupToArray());
      int ageGroupInput = InputController.validateOptionRange(ageGroupToArray().length);
      AgeGroupType ageGroupType = AgeGroupType.values()[ageGroupInput - 1];

      displayTop(style, distance, amount, GenderType.MALE, ageGroupType);
      displayTop(style, distance, amount, GenderType.FEMALE, ageGroupType);
      displayTop(style, distance, amount, GenderType.OTHER, ageGroupType);
    }
  }

  /**
   * Display top.
   *
   * @param style StyleType
   * @param distance DistanceType
   * @param amount int
   * @param genderType GenderType
   * @param ageGroupType AgeGroupType
   * @auther Mohamad, Jackie
   */
  private void displayTop(
      StyleType style,
      DistanceType distance,
      int amount,
      GenderType genderType,
      AgeGroupType ageGroupType) {
    ArrayList<ResultModel> top5 = findTop(style, distance, amount, genderType, ageGroupType);
    if (top5.isEmpty()) {
      VIEW.printWarning(
          "No results for "
              + distance.getMeters()
              + " m in "
              + style
              + ", for gender "
              + genderType.name()
              + ", in age group "
              + ageGroupType.name());
    } else {
      VIEW.printTable(
          getLeaderboardHeader(genderType.name().toLowerCase()), getLeaderboardContent(top5));
    }
  }

  /**
   * Return content used to display a table.
   *
   * @param resultModels ArrayList<ResultModel>
   * @return ArrayList<ArrayList<String>>
   * @auther Jackie, Mathias
   */
  private ArrayList<ArrayList<String>> getLeaderboardContent(ArrayList<ResultModel> resultModels) {
    ArrayList<ArrayList<String>> result = new ArrayList<>();

    for (ResultModel rm : resultModels) {
      ArrayList<String> row = new ArrayList<>();

      row.add(rm.getCompetition().getName());
      row.add(rm.getMember().getName());
      row.add(rm.getPlacement());
      row.add(String.valueOf(rm.getResultTime()));

      result.add(row);
    }

    return result;
  }

  /**
   * Returns a header used to display leaderboard table.
   *
   * @param gender String
   * @return String[]
   * @auther Jackie
   */
  private String[] getLeaderboardHeader(String gender) {
    return new String[] {"Swim event " + gender, "Name", "Placement", "time"};
  }

  /**
   * Returns a String[] from enum.
   *
   * @return String[]
   * @auther Mohamad, Mathias
   */
  public String[] ageGroupToArray() {
    String[] result = new String[AgeGroupType.values().length];

    for (int i = 0; i < result.length; i++) {
      result[i] = AgeGroupType.values()[i].name();
    }

    return result;
  }
}

package com.app.controllers;

import com.app.models.SwimEventModel;
import com.app.models.MemberModel;
import com.app.models.ResultModel;
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

  /**
   * find top swimmers in all competitions.
   *
   * @param style to check.
   * @param distance of the style.
   * @return an array of the amount the fastest swimmers in a given discipline.
   */
  private ArrayList<ResultModel> findTop(
      StyleType style,
      DistanceType distance,
      int amount,
      GenderType genderType,
      AgeGroupType ageGroupType) {
    ArrayList<ResultModel> allResults = findDiscipline(style, distance);
    if (allResults.isEmpty()) {
      return allResults; // Changed from null
    }

    ArrayList<ResultModel> result = new ArrayList<>();
    Collections.sort(allResults);
    if (amount > allResults.size()) {
      amount = allResults.size();
    }

    // TODO this crashes when there is only one result (less than "amount")
    for (int i = 0; i < amount; i++) {

      if (!memberExist(result, allResults.get(i).getMember())
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

  private String[][] arrayWithResultToDisplay(ArrayList<ResultModel> resultTimes) { //TODO DO we use this
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

      displayTop5(style, distance, 5, GenderType.MALE, ageGroupType);
      displayTop5(style, distance, 5, GenderType.FEMALE, ageGroupType);
      displayTop5(style, distance, 5, GenderType.OTHER, ageGroupType);
    }
  }

  public void displayTop5(
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
      //VIEW.displayTopResults(arrayWithResultToDisplay(top5));
      VIEW.printTable(getLeaderboardHeader(genderType.name().toLowerCase()), getLeaderboardContent(top5));
    }
  }

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

  private String[] getLeaderboardHeader(String gender) {
    return new String[] {"Swim event " + gender, "Name", "Placement", "time"};
  }

  public String[] ageGroupToArray() {
    String[] result = new String[AgeGroupType.values().length];

    for (int i = 0; i < result.length; i++) {
      result[i] = AgeGroupType.values()[i].name();
    }
    return result;
  }
}

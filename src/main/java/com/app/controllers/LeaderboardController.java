package com.app.controllers;

import com.app.models.CompetitionModel;
import com.app.models.LeaderboardModel;
import com.app.models.ResultModel;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LeaderboardController {

  private ArrayList<CompetitionModel> allResults = new CompetitionController().getCompetitions();

  /**
   * find top swimmers in all competitions.
   *
   * @param style    to check.
   * @param distance of the style.
   * @param amount   how many you want too see int the list.
   * @return an array of the amount the fastest swimmers in a given discipline.
   */
  public LeaderboardModel[] findTop(String style, int distance, int amount) {

    LeaderboardModel[] result = new LeaderboardModel[amount];

    ArrayList<ResultModel> allResults = findDiscipline(style, distance);

    Collections.sort(allResults, new Comparator<ResultModel>() {
      @Override
      public int compare(ResultModel r1, ResultModel r2) {
        return Integer.valueOf(r1.getResultTime().compareTo(r2.getResultTime()));
      }
    });

    for (int i = 0; i < result.length; i++) {
      result[i] = new LeaderboardModel(allResults.get(i).getMember().getName(), allResults.get(i).getResultTime());
    }

//    for (int i = 0; i < result.length; i++) {
//      CompetitionModel flag = null;
//      int flag1 = 0;
//      int flag2 = 0;
//      LocalTime checker = LocalTime.of(0, 0, 0, 0);
//
//      for (int j = 0; j < allResults.size(); j++) {
//        ResultModel temp = allResults.get(j);
//
//        if (temp.getResultTime().isBefore(checker)) {
//          flag2 = j;
//          flag1 = j;
//          checker = temp.getResultTime();
//          flag = allResults.get(j);
//        }
//      }
//
//      if (flag == null) {
//        return result;
//      } else {
//        competitions.get(flag1).getResult().remove(flag2);
//        result[i] =
//            new LeaderboardModel(
//                flag.getName(),
//                flag.getResult().get(flag2).getMember().getName(),
//                flag.getResult().get(flag2).getResultTime());
//      }
//    }

    return result;
  }

  private ArrayList<ResultModel> findDiscipline(String style, int distance) {

    ArrayList<ResultModel> result = new ArrayList<>();

    for (int i = 0; i < allResults.size(); i++) {

      CompetitionModel cm = allResults.get(i);
      for (int j = 0; j < cm.getResult().size(); j++) {
        if (cm.getResult().get(j).getDiscipline().getStyle().equals(style) && cm.getResult().get(j).getDiscipline().getDistance() == distance) {
          result.addAll(cm.getResult());
        }
      }
    }
    return result;
  }

}

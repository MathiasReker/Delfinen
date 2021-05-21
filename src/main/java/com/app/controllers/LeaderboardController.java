package com.app.controllers;

import com.app.models.CompetitionModel;
import com.app.models.LeaderboardModel;
import com.app.models.ResultModel;
import com.app.models.services.CompetitionService;
import com.app.views.CompetitionView;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class LeaderboardController {

  private ArrayList<CompetitionModel> allResults;
  private final CompetitionView VIEW = new CompetitionView();
  private CompetitionService competitionService;

  public void loadCompsFromFile() {
    try {
      competitionService = new CompetitionService("data/competitions.bin");
      allResults = toArraylist(competitionService.getCompetitionsFromFile());
    } catch (IOException | ClassNotFoundException e) {
      VIEW.printWarning("Could not load Competitions");
    }
  }

  /**
   * find top swimmers in all competitions.
   *
   * @param style to check.
   * @param distance of the style.
   * @param showOnList how many you want too see int the list.
   * @return an array of the 5 fastest swimmers in a given discipline.
   */

  public LeaderboardModel[] findTopFive(String style, int distance, int showOnList) {

    loadCompsFromFile();
    LeaderboardModel[] result = new LeaderboardModel[showOnList];

    ArrayList<CompetitionModel> competitions = findDiscipline(style, distance);

    for (int i = 0; i < result.length; i++) {
      CompetitionModel flag = null;
      int flag1 = 0;
      int flag2 = 0;
      LocalTime checker = LocalTime.of(0, 0, 0, 0);

      for (int p = 0; p < competitions.size(); p++) {
        ArrayList<ResultModel> times = competitions.get(p).getResult();

        for (int j = 0; j < times.size(); j++) {
          ResultModel temp = competitions.get(j).getResult().get(j);

          if (temp.getResultTime().isBefore(checker)) {
            flag2 = j;
            checker = temp.getResultTime();
            flag = competitions.get(p);
          }
        }
      }

      if (flag == null) {
        return result;
      } else {
        competitions.get(flag1).getResult().remove(flag2);
        result[i] = new LeaderboardModel(flag.getName(), flag.getResult().get(flag2).getMember().getName(), flag.getResult().get(flag2).getResultTime());
      }
    }

    return result;
  }

  private ArrayList<CompetitionModel> findDiscipline(String style, int distance) {

    ArrayList<CompetitionModel> result = new ArrayList<>();

    for (CompetitionModel cm : allResults) {
      ArrayList<ResultModel> temp = cm.getResult();
      for (ResultModel resultModel : temp) {
        if (resultModel.getDiscipline().getStyle().equals(style) && resultModel.getDiscipline().getDistance() == distance) {
          result.add(cm);
        }
      }
    }
    return result;
  }

  private ArrayList<CompetitionModel> toArraylist(CompetitionModel[] competitions) {
    ArrayList<CompetitionModel> result = new ArrayList<>();
    for (CompetitionModel c : competitions) {
      result.add(c);
    }
    return result;
  }

}
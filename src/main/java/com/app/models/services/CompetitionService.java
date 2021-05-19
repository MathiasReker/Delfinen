package com.app.models.services;

import com.app.models.CompetitionModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class CompetitionService {

  private final FileService FILE_SERVICE;

  public CompetitionService(String path) throws IOException {
    FILE_SERVICE = new FileService(path);
  }

  public void saveCompetitionsToFile(ArrayList<CompetitionModel> competitions)
      throws FileNotFoundException {
    String[] result = new String[competitions.size()];

    for (int i = 0; i < result.length; i++) {
      result[i] =
          String.join(
              ";",
              competitions.get(i).getId(),
              competitions.get(i).getName(),
              competitions.get(i).getStartDate().toString(),
              competitions.get(i).getStartTime().toString());
    }
    FILE_SERVICE.writeToFile(result);
  }

  public ArrayList<CompetitionModel> getCompetitionsFromFile() throws FileNotFoundException {
    String[] competitionString = FILE_SERVICE.readFromFile();
    ArrayList<CompetitionModel> result = new ArrayList<>();
    for (String s : competitionString) {
      String[] data = s.split(";");
      result.add(
          new CompetitionModel(
              data[0], data[1], LocalDate.parse(data[2]), LocalTime.parse(data[3])));
    }
    return result;
  }
}

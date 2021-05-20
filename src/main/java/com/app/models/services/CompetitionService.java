package com.app.models.services;

import com.app.models.CompetitionModel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CompetitionService {

  private final FileService FILE_SERVICE;

  public CompetitionService(String path) throws IOException {
    FILE_SERVICE = new FileService(path);
  }

  /*  public void saveCompetitionsToFile(ArrayList<CompetitionModel> competitions)
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
  }*/

  public void saveCompetitionsToFile(CompetitionModel[] competitions) {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      oos.writeObject(competitions);
      byte[] competitionsInBytes = baos.toByteArray();
      oos.flush();
      oos.close();
      baos.close();
      FILE_SERVICE.writeToBin(competitionsInBytes);
    } catch (IOException e) {
      System.out.println("ToDO"); // TODO move catch out to controller
    }
  }

  /*
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

   */
  public CompetitionModel[] getCompetitionsFromFile() throws IOException, ClassNotFoundException {
    byte[] competitionsInByte = FILE_SERVICE.loadFromBin();
    ByteArrayInputStream bais = new ByteArrayInputStream(competitionsInByte);
    ObjectInputStream ois = new ObjectInputStream(bais);
    CompetitionModel[] competitions = (CompetitionModel[]) ois.readObject();
    ois.close();
    bais.close();
    return competitions;
  }
}

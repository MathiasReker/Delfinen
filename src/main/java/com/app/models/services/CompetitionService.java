package com.app.models.services;

import com.app.models.CompetitionModel;

import java.io.*;

public class CompetitionService {

  private final FileService FILE_SERVICE;

  public CompetitionService(String path) throws IOException {
    FILE_SERVICE = new FileService(path);
  }

  public void saveCompetitionsToFile(CompetitionModel[] competitions) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(competitions);
    byte[] competitionsInBytes = baos.toByteArray();
    oos.flush();
    oos.close();
    baos.close();
    FILE_SERVICE.writeToBin(competitionsInBytes);
  }

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

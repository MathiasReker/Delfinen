package com.app.models.services;

import com.app.models.TrainingModel;

import java.io.*;

public class TrainingService {

  private final FileService FILE_SERVICE;

  public TrainingService(String path) throws IOException {
    FILE_SERVICE = new FileService(path);
  }

  public void saveTrainingsToFile(TrainingModel[] trainings) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(trainings);
    byte[] trainingsInBytes = baos.toByteArray();
    oos.flush();
    oos.close();
    baos.close();
    FILE_SERVICE.writeToBin(trainingsInBytes);
  }

  public TrainingModel[] getTrainingsFromFile() throws IOException, ClassNotFoundException {
    byte[] trainingsInByte = FILE_SERVICE.loadFromBin();
    ByteArrayInputStream bais = new ByteArrayInputStream(trainingsInByte);
    ObjectInputStream ois = new ObjectInputStream(bais);
    TrainingModel[] result = (TrainingModel[]) ois.readObject();
    ois.close();
    bais.close();

    return result;
  }
}

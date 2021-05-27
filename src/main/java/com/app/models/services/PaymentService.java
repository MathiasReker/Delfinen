package com.app.models.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PaymentService {
  private final FileService FILE_SERVICE;
  private final String PATH;

  public PaymentService(String path) throws IOException {
    FILE_SERVICE = new FileService(path);
    this.PATH = path;
  }

  public ArrayList<String> getApprovedPayments() throws FileNotFoundException {
    String[] competitionString = FILE_SERVICE.readFromFile();
    ArrayList<String> result = new ArrayList<>();
    for (String s : competitionString) {
      result.add(s.split(";")[0]);
    }

    return result;
  }

  public void backupToFile(ArrayList<String> strings) throws IOException {
    FileService fileService = new FileService(getFullBackupFilePath());
    String[] result = new String[strings.size()];

    for (int i = 0; i < result.length; i++) {
      result[i] = String.join(";", strings.get(i));
    }
    fileService.writeToFile(result);
  }

  /**
   * Returns backup file path.
   *
   * @return String
   * @auther Mathias
   */
  private String getFullBackupFilePath() {
    return PATH + LocalDate.now() + "-backup.txt";
  }
}

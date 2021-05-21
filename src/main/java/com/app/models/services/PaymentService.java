package com.app.models.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PaymentService {

  private final FileService FILE_SERVICE;
  private String path;

  public PaymentService(String path) throws IOException {
    FILE_SERVICE = new FileService(path);
    this.path = path;
  }

  public ArrayList<String> getApprovedPayments() throws FileNotFoundException {
    String[] competitionString = FILE_SERVICE.readFromFile();
    ArrayList<String> result = new ArrayList<>();
    for (String s : competitionString) {
      String[] data = s.split(";");
      result.add(data[0]);
    }
    return result;
  }

    public void backupToFile(ArrayList<String> strings)
        throws IOException {
    FileService fileService = new FileService(createNewPath());
    String[] result = new String[strings.size()];

    for (int i = 0; i < result.length; i++) {
      result[i] = String.join(";", strings.get(i));
    }
    fileService.writeToFile(result);
  }

  private String createNewPath(){
    String s = path;
    String [] data = s.split("/");
    data[2] = "backup.txt";
    s = String.join("/", data[0],data[1],LocalDate.now() + data[2]);
    return s;
  }
}

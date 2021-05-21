package com.app.models.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class PaymentService {

  private final FileService FILE_SERVICE;

  public PaymentService(String path) throws IOException {
    FILE_SERVICE = new FileService(path);
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
}

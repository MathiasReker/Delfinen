package com.app.models.services;

import java.io.IOException;
import java.util.ArrayList;

public class ConfigService {
  private final String KEY;

  public ConfigService(String key) {
    this.KEY = key;
  }

  public String getPath() throws IOException {
    FileService file = new FileService("data/config/config.txt");
    ArrayList<String> fileInfo = file.readTextFile();

    for (String s : fileInfo) {
      String[] line = s.split(";");

      if (line[0].equals(KEY)) {
        return line[1];
      }
    }

    return null;
  }
}

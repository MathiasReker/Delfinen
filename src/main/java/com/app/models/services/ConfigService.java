package com.app.models.services;

import java.io.IOException;

public class ConfigService {
  private final String KEY;

  public ConfigService(String key) {
    this.KEY = key;
  }

  /**
   * Returns key by value in the configuration file.
   *
   * @return String
   * @throws IOException
   * @auther Mathias
   */
  public String getPath() throws IOException {
    FileService file = new FileService("data/config/config.txt");
    String[] fileInfo = file.readFromFile();

    for (String s : fileInfo) {
      String[] line = s.split(";");

      if (line[0].equals(KEY)) {
        return line[1];
      }
    }

    return null;
  }
}

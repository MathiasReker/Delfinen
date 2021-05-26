package com.app.models.services;

import com.app.models.SwimEventModel;

import java.io.IOException;

public class SwimEventService {
  private final String PATH;

  public SwimEventService(String path) throws IOException {
    PATH = path;
  }

  public void save(SwimEventModel[] members) throws IOException {
    new ObjectService(PATH).save(members);
  }

  public SwimEventModel[] load() throws IOException, ClassNotFoundException {
    return (SwimEventModel[]) new ObjectService(PATH).load();
  }
}

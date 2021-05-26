package com.app.models.services;

import com.app.models.CompetitionModel;

import java.io.IOException;

public class CompetitionService {
  private final String PATH;

  public CompetitionService(String path) throws IOException {
    PATH = path;
  }

  public void save(CompetitionModel[] members) throws IOException {
    new ObjectService(PATH).save(members);
  }

  public CompetitionModel[] load() throws IOException, ClassNotFoundException {
    return (CompetitionModel[]) new ObjectService(PATH).load();
  }
}

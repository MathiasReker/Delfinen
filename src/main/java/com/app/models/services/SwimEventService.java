package com.app.models.services;

import com.app.models.SwimEventModel;

import java.io.IOException;

public class SwimEventService {
  private final String PATH;

  public SwimEventService(String path) throws IOException {
    PATH = path;
  }

  /**
   * Save SwimEventModel[] object.
   *
   * @param members
   * @throws IOException
   * @auther Mathias
   */
  public void save(SwimEventModel[] members) throws IOException {
    new ObjectService(PATH).save(members);
  }

  /**
   * Load SwimEventModel[] object.
   *
   * @return
   * @throws IOException
   * @throws ClassNotFoundException
   * @auther Mathias
   */
  public SwimEventModel[] load() throws IOException, ClassNotFoundException {
    return (SwimEventModel[]) new ObjectService(PATH).load();
  }
}

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
   * @param swimEvents SwimEventModel[]
   * @throws IOException
   * @auther Andreas, Mathias
   */
  public void save(SwimEventModel[] swimEvents) throws IOException {
    new ObjectService(PATH).save(swimEvents);
  }

  /**
   * Load SwimEventModel[] object.
   *
   * @return SwimEventModel[]
   * @throws IOException
   * @throws ClassNotFoundException
   * @auther Andreas, Mathias
   */
  public SwimEventModel[] load() throws IOException, ClassNotFoundException {
    return (SwimEventModel[]) new ObjectService(PATH).load();
  }
}

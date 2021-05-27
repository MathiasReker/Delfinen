package com.app.models.services;

import com.app.models.MemberModel;

import java.io.IOException;

public class MemberService {
  private final String PATH;

  /**
   * Member Service constructor.
   *
   * @param path String
   * @throws IOException
   * @auther Andreas, Mathias
   */
  public MemberService(String path) throws IOException {
    PATH = path;
  }

  /**
   * Returns MemberModel[].
   *
   * @param members MemberModel[]
   * @throws IOException
   * @auther Andreas, Mathias
   */
  public void save(MemberModel[] members) throws IOException {
    new ObjectService(PATH).save(members);
  }

  /**
   * Returns MemberModel[].
   *
   * @return MemberModel[]
   * @throws IOException
   * @throws ClassNotFoundException
   * @auther Andreas, Mathias
   */
  public MemberModel[] load() throws IOException, ClassNotFoundException {
    return (MemberModel[]) new ObjectService(PATH).load();
  }
}

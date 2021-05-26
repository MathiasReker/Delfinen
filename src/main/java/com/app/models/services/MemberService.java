package com.app.models.services;

import com.app.models.MemberModel;

import java.io.IOException;

public class MemberService {
  private final String PATH;

  public MemberService(String path) throws IOException {
    PATH = path;
  }

  public void saveMembers(MemberModel[] members) throws IOException {
    new ObjectService(PATH).save(members);
  }

  public MemberModel[] loadMembers() throws IOException, ClassNotFoundException {
    return (MemberModel[]) new ObjectService(PATH).load();
  }
}

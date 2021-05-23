package com.app.models.services;

import com.app.models.MemberModel;

import java.io.*;

public class MemberService {
  private final FileService FILE_SERVICE;

  public MemberService(String path) throws IOException {
    FILE_SERVICE = new FileService(path);
  }

  public void saveMembers(MemberModel[] members) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(members);
    byte[] membersInBytes = baos.toByteArray();
    oos.flush();
    oos.close();
    baos.close();
    FILE_SERVICE.writeToBin(membersInBytes);
  }

  public MemberModel[] loadMembers() throws IOException, ClassNotFoundException {
    byte[] membersInByte = FILE_SERVICE.loadFromBin();
    ByteArrayInputStream bais = new ByteArrayInputStream(membersInByte);
    ObjectInputStream ois = new ObjectInputStream(bais);
    MemberModel[] result = (MemberModel[]) ois.readObject();
    ois.close();
    bais.close();

    return result;
  }
}

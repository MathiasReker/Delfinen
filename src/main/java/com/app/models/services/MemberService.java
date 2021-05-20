package com.app.models.services;

import com.app.models.MemberModel;

import java.io.*;
import java.sql.SQLOutput;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MemberService {
  private final FileService FILE_SERVICE;

  public MemberService(String path) throws IOException {
    FILE_SERVICE = new FileService(path);
  }

  public void storeMembers(ArrayList<MemberModel> members) throws FileNotFoundException {
    String[] result = new String[members.size()];

    for (int i = 0; i < members.size(); i++) {
      MemberModel member = members.get(i);

      result[i] =
          String.join(
              ";",
              member.getID(),
              member.getName(),
              member.getMail(),
              member.getGender().name(),
              member.getBirthdate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
              member.getPhoneNumber(),
              String.valueOf(member.isCompetitive()),
              member.getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    FILE_SERVICE.writeToFile(result);
  }

  public String[] loadMembers() throws FileNotFoundException {
    return FILE_SERVICE.readFromFile();
  }

  public void saveMembers(MemberModel[] members) {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      oos.writeObject(members);
      byte[] membersInBytes = baos.toByteArray();
      oos.flush();
      oos.close();
      baos.close();
      FILE_SERVICE.writeToBin(membersInBytes);
    } catch (IOException e) {
      System.out.println("SaveMEmbers fucked");
    }
  }

  public MemberModel[] loadMembers2() throws IOException, ClassNotFoundException {
    byte[] membersInByte = FILE_SERVICE.loadFromBin();
    ByteArrayInputStream bais = new ByteArrayInputStream(membersInByte);
    ObjectInputStream ois = new ObjectInputStream(bais);
    MemberModel[] members = (MemberModel[]) ois.readObject();
    ois.close();
    bais.close();
    return members;
  }
}

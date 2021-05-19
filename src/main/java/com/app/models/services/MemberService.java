package com.app.models.services;

import com.app.models.MemberModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
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



      MemberModel m = members.get(i);

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      String formattedString = m.getBirthdate().format(formatter);

      result[i] =
          String.join(
              ";",
              m.getID(),
              m.getName(),
              m.getMail(),
              m.getGender().name(),
              formattedString,
              m.getPhoneNumber());
    }

    FILE_SERVICE.writeToFile(result);
  }

  public String[] loadMembers() throws FileNotFoundException {
    String[] strings = FILE_SERVICE.readFromFile();

    return strings;
    // String[] result = new String[strings.length];

    /* for (int i = 0; i < strings.length; i++) {
      result[i] = String.valueOf(strings[i].split(";"));
    }

    return result;*/
  }
}

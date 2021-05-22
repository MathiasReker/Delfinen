package com.app.views;

import java.util.ArrayList;

public class MemberView extends View {
  public void displayMember(String[] member, int[] columnWidth) {
    for (int i = 0; i < member.length; i++) {
      String s = member[i];

      if (s == null) {
        s = "--";
      }

      System.out.printf("%-" + (columnWidth[i] + 4) + "s", s);
    }

    System.out.println();
  }

  public void displaySortedMembers(ArrayList<String> members) {
    for (String s : members) {
      print(s);
    }
    System.out.println();
  }
}

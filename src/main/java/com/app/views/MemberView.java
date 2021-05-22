package com.app.views;

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
}

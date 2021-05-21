package com.app.views;

import java.util.ArrayList;

public class MemberView extends View {
  public void displayOptions(String[] gender) {
    String[] result = new String[gender.length];

    for (int i = 0; i < gender.length; i++) {
      result[i] = gender[i] + " [" + (i + 1) + "]";
    }

    printInline(String.join(", ", result) + ": ");
  }

  public void displayMembers(ArrayList<String> arr) {
    for (String s : arr) {
      print(s);
    }
    System.out.println();
  }

  public void displaySortedMembers(ArrayList<String> members) { // TODO: move to View
    for (String s : members) {
      print(s);
    }
    System.out.println(); // TODO
  }
}

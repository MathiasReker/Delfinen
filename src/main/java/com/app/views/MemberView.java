package com.app.views;

public class MemberView extends View {
  public void displayGenderMenu(String[] gender) {
    String[] result = new String[gender.length];

    for (int i = 0; i < gender.length; i++) {
      result[i] = gender[i] + " [" + (i + 1) + "]";
    }

    printInline(String.join(", ", result) + ": ");
  }
}

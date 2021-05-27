package com.app.views;

import com.app.views.types.ColorKeyType;
import com.app.views.types.ColorTextType;

import java.util.ArrayList;

/** @auther Mathias */
public class TableView {
  private final String[] HEADERS;
  private final ArrayList<ArrayList<String>> BODY;
  private final ArrayList<Integer> MAX_LENGTH;

  public TableView(String[] headerIn, ArrayList<ArrayList<String>> bodyIn) {
    this.HEADERS = headerIn;
    this.MAX_LENGTH = new ArrayList<>();

    for (String header : HEADERS) {
      MAX_LENGTH.add(header.length());
    }

    this.BODY = bodyIn;
    calcMaxLengthAll();
  }

  /** @auther Mathias */
  public void printTable() {
    System.out.println();

    StringBuilder padding = new StringBuilder();
    int tablePadding = 1;
    padding.append(" ".repeat(tablePadding));

    // Create rowSeparator
    StringBuilder sbRowSep = new StringBuilder();
    for (Integer i : MAX_LENGTH) {
      sbRowSep.append("+");
      char separator = '-';
      sbRowSep.append(String.valueOf(separator).repeat(Math.max(0, i + (tablePadding * 2))));
    }

    sbRowSep.append("+");
    String rowSeparator = sbRowSep.toString();

    StringBuilder sb = new StringBuilder();
    sb.append(rowSeparator);
    sb.append("\n");

    // Create header
    sb.append("|");
    for (int i = 0; i < HEADERS.length; i++) {
      sb.append(padding);
      sb.append(new ColorTextType(HEADERS[i], ColorKeyType.BLUE_BOLD_BRIGHT));

      // Fill up with empty spaces
      sb.append(" ".repeat(Math.max(0, (MAX_LENGTH.get(i) - HEADERS[i].length()))));
      sb.append(padding);
      sb.append("|");
    }
    sb.append("\n");
    sb.append(rowSeparator);
    sb.append("\n");

    // Create body
    for (ArrayList<String> tempRow : BODY) {
      // New row
      sb.append("|");
      for (int j = 0; j < tempRow.size(); j++) {
        sb.append(padding);
        sb.append(new ColorTextType(tempRow.get(j), ColorKeyType.WHITE_BRIGHT));

        // Add empty spaces
        sb.append(" ".repeat(Math.max(0, (MAX_LENGTH.get(j) - tempRow.get(j).length()))));
        sb.append(padding);
        sb.append("|");
      }
      sb.append("\n");
      sb.append(rowSeparator);
      sb.append("\n");
    }

    System.out.print(sb);
  }

  /**
   * Update maxLength to make the column width dynamic.
   *
   * @auther Mathias
   */
  private void calcMaxLengthAll() {
    for (ArrayList<String> temp : BODY) {
      for (int j = 0; j < temp.size(); j++) {
        if (temp.get(j).length() > MAX_LENGTH.get(j)) {
          MAX_LENGTH.set(j, temp.get(j).length());
        }
      }
    }
  }
}

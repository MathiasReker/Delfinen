package com.app.views;

import com.app.views.types.ColorKeyType;
import com.app.views.types.ColorTextType;

import java.util.ArrayList;

public class TableView {
  private final ArrayList<String> headers;
  private final ArrayList<ArrayList<String>> table;
  private final ArrayList<Integer> maxLength;

  public TableView(ArrayList<String> headersIn, ArrayList<ArrayList<String>> content) {
    this.headers = headersIn;
    this.maxLength = new ArrayList<>();

    for (String header : headers) {
      maxLength.add(header.length());
    }

    this.table = content;
    calcMaxLengthAll();
  }

  public void printTable() {
    StringBuilder padding = new StringBuilder();
    int tablePadding = 2;
    padding.append(" ".repeat(tablePadding));

    // Create rowSeparator
    StringBuilder sbRowSep = new StringBuilder();
    for (Integer i : maxLength) {
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
    for (int i = 0; i < headers.size(); i++) {
      sb.append(padding);
      sb.append(new ColorTextType(headers.get(i), ColorKeyType.BLUE_BOLD_BRIGHT));

      // Fill up with empty spaces
      sb.append(" ".repeat(Math.max(0, (maxLength.get(i) - headers.get(i).length()))));
      sb.append(padding);
      sb.append("|");
    }
    sb.append("\n");
    sb.append(rowSeparator);
    sb.append("\n");

    // Create body
    for (ArrayList<String> tempRow : table) {
      // New row
      sb.append("|");
      for (int j = 0; j < tempRow.size(); j++) {
        sb.append(padding);
        sb.append(new ColorTextType(tempRow.get(j), ColorKeyType.WHITE_BRIGHT));

        // Add empty spaces
        sb.append(" ".repeat(Math.max(0, (maxLength.get(j) - tempRow.get(j).length()))));
        sb.append(padding);
        sb.append("|");
      }
      sb.append("\n");
      sb.append(rowSeparator);
      sb.append("\n");
    }
    System.out.println(sb);
  }

  // Update maxLength to make the column width dynamic
  private void calcMaxLengthAll() {
    for (ArrayList<String> temp : table) {
      for (int j = 0; j < temp.size(); j++) {
        if (temp.get(j).length() > maxLength.get(j)) {
          maxLength.set(j, temp.get(j).length());
        }
      }
    }
  }
}

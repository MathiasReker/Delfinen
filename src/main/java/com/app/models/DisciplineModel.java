package com.app.models;

import java.util.ArrayList;

public class DisciplineModel {

  private int distance;
  private String style;

  /**
   * Create the swim style, with a given style and distance.
   * 
   * @param distance of the swim style.
   * @param style you want to use.
   */
  public DisciplineModel (int distance, String style) {
    this.distance = distance;
    this.style = style;
  }

  /**
   * Get the distance for the chosen swim style.
   *
   * @return the distance.
   */
  public int getDistance() {
    return distance;
  }

  /**
   * Get the chosen swim style.
   *
   * @return the swim style.
   */
  public String getStyle() {
    return style;
  }

  /**
   * Take an input to give the following style.
   *
   * @param choice the input that define whit style been chosen.
   * @return the chosen style.
   */
  public String chosenStyle(int choice) {
    // TODO: 17-05-2021 skulle nok flyttes til en anden klasse
    return StyleModel.values()[choice].toString();
  }

  /**
   * Output a list with the distances for the specific swim style.
   *
   * @param gender gets the gender, 1 is boy, 2 is girl, 3 is other
   * @param style  that define the distance.
   * @return a list og distances.
   */
  public ArrayList<String> chosenDistance(int gender, String style) {
    // TODO: 17-05-2021 skulle nok flyttes til en anden klasse

    ArrayList<String> Distances = new ArrayList<>();

    if (style.equals(StyleModel.BUTTERFLY.toString())
        || style.equals(StyleModel.BACKSTROKE.toString())
        || style.equals(StyleModel.BACKSTROKE.toString())) {
      Distances.add(Distance.FIFTY + " m");
      Distances.add(Distance.HUNDRED + " m");
      Distances.add(Distance.TWO_HUNDRED + " m");
    } else if (style.equals(StyleModel.FREESTYLE.toString())) {

      Distances.add(Distance.FIFTY + " m");
      Distances.add(Distance.HUNDRED + " m");
      Distances.add(Distance.TWO_HUNDRED + " m");
      Distances.add(Distance.FOUR_HUNDRED + " m");
      if (gender == 1) {
        Distances.add(Distance.FIFTEEN_HUNDRED + " m");
      } else if (gender == 2) {
        Distances.add(Distance.EIGHT_HUNDRED + " m");
      } else {
        Distances.add(Distance.EIGHT_HUNDRED + " m");
        Distances.add(Distance.FIFTEEN_HUNDRED + " m");
      }

    } else if (style.equals(StyleModel.MEDLEY.toString())) {
      Distances.add(Distance.FIFTY + " m");
      Distances.add(Distance.HUNDRED + " m");
      Distances.add(Distance.TWO_HUNDRED + " m");
      Distances.add(Distance.FOUR_HUNDRED + " m");
    } else {
      throw new IllegalArgumentException("No such style");
    }

    return Distances;
  }


}

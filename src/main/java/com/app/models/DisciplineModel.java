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
  public DisciplineModel(int distance, String style) {
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


}

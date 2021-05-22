package com.app.models;

import java.io.Serializable;

public class DisciplineModel implements Serializable {
  private final int DISTANCE;
  private final String STYLE;

  /**
   * Create the swim style, with a given style and distance.
   *
   * @param distance of the swim style.
   * @param style you want to use.
   */
  public DisciplineModel(int distance, String style) {
    this.DISTANCE = distance;
    this.STYLE = style;
  }

  /**
   * Get the distance for the chosen swim style.
   *
   * @return the distance.
   */
  public int getDistance() {
    return DISTANCE;
  }

  /**
   * Get the chosen swim style.
   *
   * @return the swim style.
   */
  public String getStyle() {
    return STYLE;
  }

  /**
   * Take an input to give the following style.
   *
   * @param choice the input that define whit style been chosen.
   * @return the chosen style.
   */
  public String chosenStyle(int choice) { // TODO: Method 'chosenStyle(int)' is never used
    // TODO: 17-05-2021 skulle nok flyttes til en anden klasse
    return StyleModel.values()[choice].toString();
  }
}

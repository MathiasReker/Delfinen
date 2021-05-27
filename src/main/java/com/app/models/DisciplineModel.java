package com.app.models;

import com.app.models.types.DistanceType;
import com.app.models.types.StyleType;

import java.io.Serializable;

public class DisciplineModel implements Serializable {
  private final DistanceType DISTANCE;
  private final StyleType STYLE;

  /**
   * Create the swim style, with a given style and distance.
   *
   * @param distance of the swim style.
   * @param style you want to use.
   */
  public DisciplineModel(DistanceType distance, StyleType style) {
    this.DISTANCE = distance;
    this.STYLE = style;
  }

  /**
   * Get the distance for the chosen swim style.
   *
   * @return the distance.
   */
  public DistanceType getDistance() {
    return DISTANCE;
  }

  /**
   * Get the chosen swim style.
   *
   * @return the swim style.
   */
  public StyleType getStyle() {
    return STYLE;
  }

  public String getDisciplineName() {
    return getDistance().getMeters() + "m " + getStyle().name();
  }
}

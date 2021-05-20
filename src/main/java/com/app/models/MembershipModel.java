package com.app.models;

import java.io.Serializable;
import java.time.LocalDate;

public class MembershipModel implements Serializable {
  private final String ID;
  private LocalDate startingDate;
  private LocalDate expiringDate;
  private boolean payed;
  private boolean active;

  public MembershipModel() { // TODO Get next UniqueValue
    this.ID = "NextUniqueValue";
  }
  /**
   * Constructor to instantiate new objects in model.
   *
   * <p>For example when reading from DB
   *
   * @param id Unique ID value
   * @param startingDate
   * @param expiringDate
   * @param payed PaymentStatus
   * @param active
   */
  MembershipModel(
      String id, LocalDate startingDate, LocalDate expiringDate, boolean payed, boolean active) {
    this.ID = id;
    setStartingDate(startingDate);
    setExpiringDate(expiringDate);
    setPayed(payed);
    setActive(active);
  }

  public String getID() {
    return ID;
  }

  public LocalDate getExpiringDate() {
    return expiringDate;
  }

  public void setExpiringDate(LocalDate expiringDate) {
    this.expiringDate = expiringDate;
  }

  public LocalDate getStartingDate() {
    return startingDate;
  }

  public void setStartingDate(LocalDate startingDate) {
    this.startingDate = startingDate;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public boolean isPayed() {
    return payed;
  }

  public void setPayed(boolean payed) {
    this.payed = payed;
  }
}

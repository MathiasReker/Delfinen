package com.app.models;

import java.io.Serializable;
import java.time.LocalDate;

public class MembershipModel implements Serializable {
  private final String ID;
  private final LocalDate CREATION_DATE;
  private LocalDate startingDate;
  private LocalDate expiringDate;
  private boolean paid;
  private boolean active;

  public MembershipModel(String id) {
    this.ID = id;
    CREATION_DATE = LocalDate.now();
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

  public boolean isPaid() {
    return paid;
  }

  public void setPaid(boolean paid) {
    this.paid = paid;
  }
}

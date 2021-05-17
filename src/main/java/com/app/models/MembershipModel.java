package com.app.models;

import java.time.LocalDate;

public class MembershipModel {
    private final String ID;
    private LocalDate startingDate;
    private LocalDate expiringDate;
    private boolean payed;
    private boolean active;


    public MembershipModel(){ //TODO Get next UniqueValue
        this.ID = "NextUniqueValue";
    }
    /**
     * Constructor to instantiate new objects in model.
     *
     * For example when reading from db
     *
     * @param id Unique ID value
     * @param startingDate
     * @param expiringDate
     * @param payed PaymentStatus
     * @param active 
     */

    MembershipModel(String id, LocalDate startingDate, LocalDate expiringDate, boolean payed, boolean active){
        this.ID = id;
        setStartingDate(startingDate);
        setExpiringDate(expiringDate);
        setPayed(payed);
        setActive(active);
    }


    public void setActive(boolean active) {
        this.active = active;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public void setExpiringDate(LocalDate expiringDate) {
        this.expiringDate = expiringDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }


    public String getID() {
        return ID;
    }

    public LocalDate getExpiringDate() {
        return expiringDate;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isPayed() {
        return payed;
    }
}

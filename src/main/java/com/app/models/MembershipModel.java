package com.app.models;

import java.util.Date;

public class MembershipModel {
    private final String ID;
    private Date startingDate;
    private Date expiringDate;
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
     * @param id
     * @param startingDate
     * @param expiringDate
     * @param payed
     * @param active
     */

    MembershipModel(String id, Date startingDate, Date expiringDate, boolean payed, boolean active){
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

    public void setExpiringDate(Date expiringDate) {
        this.expiringDate = expiringDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }
}

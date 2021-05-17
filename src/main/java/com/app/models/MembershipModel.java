package com.app.models;

import java.util.Date;

public class MembershipModel {
    private String ID;
    private Date startingDate;
    private Date expiringDate;
    private boolean payed;
    private boolean active;


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

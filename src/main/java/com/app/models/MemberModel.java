package com.app.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MemberModel {
    private String ID;
    private String name;
    private LocalDateTime age;
    private Gender gender;
    private string phoneNumber;
    private CompetetivenessLevel level;
    private ArrayList<Disciplines> disciplines;
    private final LocalDateTime creationDate;
    private ArrayList<MembershipModel> memberships;


    public MemberModel(){
        creationDate = LocalDateTime.now();
    }


}

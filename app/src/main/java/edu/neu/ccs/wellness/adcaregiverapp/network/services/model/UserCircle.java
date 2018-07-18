package edu.neu.ccs.wellness.adcaregiverapp.network.services.model;

import java.util.ArrayList;

public class UserCircle {
    private int id;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private ArrayList<Member> members;

    public ArrayList<Member> getMembers() {
        return this.members;
    }

    public void setMembers(ArrayList<Member> members) {
        this.members = members;
    }
}

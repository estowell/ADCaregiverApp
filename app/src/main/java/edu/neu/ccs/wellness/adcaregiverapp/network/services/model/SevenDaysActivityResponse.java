package edu.neu.ccs.wellness.adcaregiverapp.network.services.model;

import java.util.ArrayList;

public class SevenDaysActivityResponse {

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

    private ArrayList<GroupActivities> activities;

    public ArrayList<GroupActivities> getActivities() {
        return this.activities;
    }

    public void setActivities(ArrayList<GroupActivities> activities) {
        this.activities = activities;
    }
}

package edu.neu.ccs.wellness.adcaregiverapp.network.services.model;

import java.util.ArrayList;
import java.util.Date;

public class GroupActivities {

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

    private Date last_pull_time;

    public Date getLastPullTime() {
        return this.last_pull_time;
    }

    public void setLastPullTime(Date last_pull_time) {
        this.last_pull_time = last_pull_time;
    }

    private String role;

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private ArrayList<UserActivities> activities;

    public ArrayList<UserActivities> getActivities() {
        return this.activities;
    }

    public void setActivities(ArrayList<UserActivities> activities) {
        this.activities = activities;
    }


}

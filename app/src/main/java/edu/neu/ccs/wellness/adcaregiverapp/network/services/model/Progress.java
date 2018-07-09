package edu.neu.ccs.wellness.adcaregiverapp.network.services.model;

public class Progress {

    private int person_id;

    public int getPersonId() {
        return this.person_id;
    }

    public void setPersonId(int person_id) {
        this.person_id = person_id;
    }

    private int goal;

    public int getGoal() {
        return this.goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    private String unit;

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    private String unit_duration;

    public String getUnitDuration() {
        return this.unit_duration;
    }

    public void setUnitDuration(String unit_duration) {
        this.unit_duration = unit_duration;
    }

}

package edu.neu.ccs.wellness.adcaregiverapp.network.services.model;

public class UnlockedFlowersModel {

    boolean week1;
    boolean week2;
    boolean week3;

    public UnlockedFlowersModel() {
    }

    public UnlockedFlowersModel(boolean week1, boolean week2, boolean week3) {
        this.week1 = week1;
        this.week2 = week2;
        this.week3 = week3;
    }

    public boolean isWeek1() {
        return week1;
    }

    public boolean isWeek2() {
        return week2;
    }

    public boolean isWeek3() {
        return week3;
    }

    public void setWeek1(boolean week1) {
        this.week1 = week1;
    }

    public void setWeek2(boolean week2) {
        this.week2 = week2;
    }

    public void setWeek3(boolean week3) {
        this.week3 = week3;
    }
}

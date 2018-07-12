package edu.neu.ccs.wellness.adcaregiverapp.domain.activities.model;

import java.util.Date;

public class Activities {

    private Date date;

    private int steps;


    public Activities(Date date, int steps) {
        this.date = date;
        this.steps = steps;
    }

    public int getSteps() {
        return this.steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

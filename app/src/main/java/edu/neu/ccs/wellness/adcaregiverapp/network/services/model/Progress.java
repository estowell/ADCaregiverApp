package edu.neu.ccs.wellness.adcaregiverapp.network.services.model;

import android.support.annotation.Nullable;

import java.util.ArrayList;

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

    @Nullable
    private ArrayList<Integer> progress;

    @Nullable
    public ArrayList<Integer> getProgress() {
        return this.progress;
    }

    public void setProgress(@Nullable ArrayList<Integer> progress) {
        this.progress = progress;
    }

    @Nullable
    private ArrayList<Double> progress_percent;

    @Nullable
    public ArrayList<Double> getProgressPercent() {
        return this.progress_percent;
    }

    public void setProgressPercent(@Nullable ArrayList<Double> progress_percent) {
        this.progress_percent = progress_percent;
    }

    @Nullable
    private ArrayList<Boolean> progress_achieved;

    @Nullable
    public ArrayList<Boolean> getProgressAchieved() {
        return this.progress_achieved;
    }

    public void setProgressAchieved(@Nullable ArrayList<Boolean> progress_achieved) {
        this.progress_achieved = progress_achieved;
    }

    private int total_progress;

    public int getTotalProgress() {
        return this.total_progress;
    }

    public void setTotalProgress(int total_progress) {
        this.total_progress = total_progress;
    }

}

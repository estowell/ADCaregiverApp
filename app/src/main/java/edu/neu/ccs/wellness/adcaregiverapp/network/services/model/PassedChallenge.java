package edu.neu.ccs.wellness.adcaregiverapp.network.services.model;

import java.util.ArrayList;
import java.util.Date;

public class PassedChallenge {
    private boolean is_currently_running;

    public boolean getIsCurrentlyRunning() {
        return this.is_currently_running;
    }

    public void setIsCurrentlyRunning(boolean is_currently_running) {
        this.is_currently_running = is_currently_running;
    }

    private String text;

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String subtext;

    public String getSubtext() {
        return this.subtext;
    }

    public void setSubtext(String subtext) {
        this.subtext = subtext;
    }

    private String total_duration;

    public String getTotalDuration() {
        return this.total_duration;
    }

    public void setTotalDuration(String total_duration) {
        this.total_duration = total_duration;
    }

    private Date start_datetime;

    public Date getStartDatetime() {
        return this.start_datetime;
    }

    public void setStartDatetime(Date start_datetime) {
        this.start_datetime = start_datetime;
    }

    private Date end_datetime;

    public Date getEndDatetime() {
        return this.end_datetime;
    }

    public void setEndDatetime(Date end_datetime) {
        this.end_datetime = end_datetime;
    }

    private int level_id;

    public int getLevelId() {
        return this.level_id;
    }

    public void setLevelId(int level_id) {
        this.level_id = level_id;
    }

    private int level_order;

    public int getLevelOrder() {
        return this.level_order;
    }

    public void setLevelOrder(int level_order) {
        this.level_order = level_order;
    }

    private ArrayList<Progress> progress;

    public ArrayList<Progress> getProgress() {
        return this.progress;
    }

    public void setProgress(ArrayList<Progress> progress) {
        this.progress = progress;
    }
}
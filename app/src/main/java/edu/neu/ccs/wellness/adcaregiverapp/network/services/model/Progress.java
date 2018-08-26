package edu.neu.ccs.wellness.adcaregiverapp.network.services.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class Progress implements Parcelable{

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.person_id);
        dest.writeInt(this.goal);
        dest.writeString(this.unit);
        dest.writeString(this.unit_duration);
        dest.writeList(this.progress);
        dest.writeList(this.progress_percent);
        dest.writeList(this.progress_achieved);
        dest.writeInt(this.total_progress);
    }

    public Progress() {
    }

    protected Progress(Parcel in) {
        this.person_id = in.readInt();
        this.goal = in.readInt();
        this.unit = in.readString();
        this.unit_duration = in.readString();
        this.progress = new ArrayList<Integer>();
        in.readList(this.progress, Integer.class.getClassLoader());
        this.progress_percent = new ArrayList<Double>();
        in.readList(this.progress_percent, Double.class.getClassLoader());
        this.progress_achieved = new ArrayList<Boolean>();
        in.readList(this.progress_achieved, Boolean.class.getClassLoader());
        this.total_progress = in.readInt();
    }

    public static final Creator<Progress> CREATOR = new Creator<Progress>() {
        @Override
        public Progress createFromParcel(Parcel source) {
            return new Progress(source);
        }

        @Override
        public Progress[] newArray(int size) {
            return new Progress[size];
        }
    };
}

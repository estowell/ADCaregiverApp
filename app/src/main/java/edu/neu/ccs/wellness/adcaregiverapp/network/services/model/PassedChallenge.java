package edu.neu.ccs.wellness.adcaregiverapp.network.services.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class PassedChallenge implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.is_currently_running ? (byte) 1 : (byte) 0);
        dest.writeString(this.text);
        dest.writeString(this.subtext);
        dest.writeString(this.total_duration);
        dest.writeLong(this.start_datetime != null ? this.start_datetime.getTime() : -1);
        dest.writeLong(this.end_datetime != null ? this.end_datetime.getTime() : -1);
        dest.writeInt(this.level_id);
        dest.writeInt(this.level_order);
        dest.writeList(this.progress);
    }

    public PassedChallenge() {
    }

    protected PassedChallenge(Parcel in) {
        this.is_currently_running = in.readByte() != 0;
        this.text = in.readString();
        this.subtext = in.readString();
        this.total_duration = in.readString();
        long tmpStart_datetime = in.readLong();
        this.start_datetime = tmpStart_datetime == -1 ? null : new Date(tmpStart_datetime);
        long tmpEnd_datetime = in.readLong();
        this.end_datetime = tmpEnd_datetime == -1 ? null : new Date(tmpEnd_datetime);
        this.level_id = in.readInt();
        this.level_order = in.readInt();
        this.progress = new ArrayList<Progress>();
        in.readList(this.progress, Progress.class.getClassLoader());
    }

    public static final Creator<PassedChallenge> CREATOR = new Creator<PassedChallenge>() {
        @Override
        public PassedChallenge createFromParcel(Parcel source) {
            return new PassedChallenge(source);
        }

        @Override
        public PassedChallenge[] newArray(int size) {
            return new PassedChallenge[size];
        }
    };
}
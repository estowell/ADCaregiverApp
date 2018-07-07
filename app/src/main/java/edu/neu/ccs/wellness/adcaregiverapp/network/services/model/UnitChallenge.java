package edu.neu.ccs.wellness.adcaregiverapp.network.services.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class UnitChallenge implements Parcelable{


    @SerializedName("option")
    public int option;

    public int getOption() {
        return this.option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    @SerializedName("goal")
    public int goal;

    public int getGoal() {
        return this.goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    @SerializedName("unit")
    public String unit;

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @SerializedName("unit_duration")
    public String unit_duration;

    public String getUnitDuration() {
        return this.unit_duration;
    }

    public void setUnitDuration(String unit_duration) {
        this.unit_duration = unit_duration;
    }

    @SerializedName("total_duration")
    public String total_duration;

    public String getTotalDuration() {
        return this.total_duration;
    }

    public void setTotalDuration(String total_duration) {
        this.total_duration = total_duration;
    }

    @SerializedName("text")
    public String text;

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @SerializedName("level_id")
    public int level_id;

    public int getLevelId() {
        return this.level_id;
    }

    public void setLevelId(int level_id) {
        this.level_id = level_id;
    }

    public UnitChallenge(int option, int goal, String unit, String unit_duration, String total_duration, String text, int level_id) {
        this.option = option;
        this.goal = goal;
        this.unit = unit;
        this.unit_duration = unit_duration;
        this.total_duration = total_duration;
        this.text = text;
        this.level_id = level_id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.option);
        dest.writeInt(this.goal);
        dest.writeString(this.unit);
        dest.writeString(this.unit_duration);
        dest.writeString(this.total_duration);
        dest.writeString(this.text);
        dest.writeInt(this.level_id);
    }

    protected UnitChallenge(Parcel in) {
        this.option = in.readInt();
        this.goal = in.readInt();
        this.unit = in.readString();
        this.unit_duration = in.readString();
        this.total_duration = in.readString();
        this.text = in.readString();
        this.level_id = in.readInt();
    }

    public static final Creator<UnitChallenge> CREATOR = new Creator<UnitChallenge>() {
        @Override
        public UnitChallenge createFromParcel(Parcel source) {
            return new UnitChallenge(source);
        }

        @Override
        public UnitChallenge[] newArray(int size) {
            return new UnitChallenge[size];
        }
    };
}

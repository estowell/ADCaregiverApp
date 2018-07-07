package edu.neu.ccs.wellness.adcaregiverapp.network.services.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AvailableChallenges implements Parcelable{

    @SerializedName("is_currently_running")
    public Boolean isCurrentlyRunning;

    @SerializedName("text")
    public String text;

    @SerializedName("subtext")
    public String subtext;

    @SerializedName("total_duration")
    public String totalDuration;

    @SerializedName("level_id")
    public int levelId;

    @SerializedName("level_order")
    public int levelOrder;

    @SerializedName("challenges")
    public List<UnitChallenge> challenges;


    public AvailableChallenges(Boolean isCurrentlyRunning, String text, String subtext, String totalDuration, int levelId, int levelOrder, List<UnitChallenge> challenges) {
        this.isCurrentlyRunning = isCurrentlyRunning;
        this.text = text;
        this.subtext = subtext;
        this.totalDuration = totalDuration;
        this.levelId = levelId;
        this.levelOrder = levelOrder;
        this.challenges = challenges;
    }

    public Boolean getCurrentlyRunning() {
        return isCurrentlyRunning;
    }

    public void setCurrentlyRunning(Boolean currentlyRunning) {
        isCurrentlyRunning = currentlyRunning;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubtext() {
        return subtext;
    }

    public void setSubtext(String subtext) {
        this.subtext = subtext;
    }

    public String getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(String totalDuration) {
        this.totalDuration = totalDuration;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getLevelOrder() {
        return levelOrder;
    }

    public void setLevelOrder(int levelOrder) {
        this.levelOrder = levelOrder;
    }

    public List<UnitChallenge> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<UnitChallenge> challenges) {
        this.challenges = challenges;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.isCurrentlyRunning);
        dest.writeString(this.text);
        dest.writeString(this.subtext);
        dest.writeString(this.totalDuration);
        dest.writeInt(this.levelId);
        dest.writeInt(this.levelOrder);
        dest.writeTypedList(this.challenges);
    }

    protected AvailableChallenges(Parcel in) {
        this.isCurrentlyRunning = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.text = in.readString();
        this.subtext = in.readString();
        this.totalDuration = in.readString();
        this.levelId = in.readInt();
        this.levelOrder = in.readInt();
        this.challenges = in.createTypedArrayList(UnitChallenge.CREATOR);
    }

    public static final Creator<AvailableChallenges> CREATOR = new Creator<AvailableChallenges>() {
        @Override
        public AvailableChallenges createFromParcel(Parcel source) {
            return new AvailableChallenges(source);
        }

        @Override
        public AvailableChallenges[] newArray(int size) {
            return new AvailableChallenges[size];
        }
    };
}

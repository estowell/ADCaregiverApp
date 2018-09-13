package edu.neu.ccs.wellness.adcaregiverapp.network.services.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

import java.util.Map;

@IgnoreExtraProperties
public class CurrentChallenge implements Parcelable{

    private int NumberOfPosts;
    private int NumberOfExerciseLogs;
    private boolean isRunning;
    private boolean passed;
    private boolean flowerUnlocked;

    private SelectedFlower selectedFlower;
    Long time;


    public CurrentChallenge() {
    }

    public CurrentChallenge(int numberOfPosts, int numberOfExcerciseLogs, boolean isRunning, boolean passed, boolean flowerUnlocked, SelectedFlower selectedFlower) {
        NumberOfPosts = numberOfPosts;
        NumberOfExerciseLogs = numberOfExcerciseLogs;
        this.isRunning = isRunning;
        this.passed = passed;
        this.flowerUnlocked = flowerUnlocked;
        this.selectedFlower = selectedFlower;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public int getNumberOfPosts() {
        return NumberOfPosts;
    }

    public void setNumberOfPosts(int numberOfPosts) {
        NumberOfPosts = numberOfPosts;
    }

    public int getNumberOfExerciseLogs() {
        return NumberOfExerciseLogs;
    }

    public void setNumberOfExerciseLogs(int numberOfExerciseLogs) {
        NumberOfExerciseLogs = numberOfExerciseLogs;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public SelectedFlower getSelectedFlower() {
        return selectedFlower;
    }

    public void setSelectedFlower(SelectedFlower selectedFlower) {
        this.selectedFlower = selectedFlower;
    }

    public Map<String, String> getTime() {
        return ServerValue.TIMESTAMP;
    }

    @Exclude
    public Long getTimeLong() {
        return time;
    }


    public boolean isFlowerUnlocked() {
        return flowerUnlocked;
    }

    public void setFlowerUnlocked(boolean flowerUnlocked) {
        this.flowerUnlocked = flowerUnlocked;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.NumberOfPosts);
        dest.writeInt(this.NumberOfExerciseLogs);
        dest.writeByte(this.isRunning ? (byte) 1 : (byte) 0);
        dest.writeByte(this.passed ? (byte) 1 : (byte) 0);
        dest.writeByte(this.flowerUnlocked ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.selectedFlower, flags);
        dest.writeValue(this.time);
    }

    protected CurrentChallenge(Parcel in) {
        this.NumberOfPosts = in.readInt();
        this.NumberOfExerciseLogs = in.readInt();
        this.isRunning = in.readByte() != 0;
        this.passed = in.readByte() != 0;
        this.flowerUnlocked = in.readByte() != 0;
        this.selectedFlower = in.readParcelable(SelectedFlower.class.getClassLoader());
        this.time = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Creator<CurrentChallenge> CREATOR = new Creator<CurrentChallenge>() {
        @Override
        public CurrentChallenge createFromParcel(Parcel source) {
            return new CurrentChallenge(source);
        }

        @Override
        public CurrentChallenge[] newArray(int size) {
            return new CurrentChallenge[size];
        }
    };
}

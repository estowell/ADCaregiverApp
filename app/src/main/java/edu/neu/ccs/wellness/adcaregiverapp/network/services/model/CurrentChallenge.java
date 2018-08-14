package edu.neu.ccs.wellness.adcaregiverapp.network.services.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.util.Map;

public class CurrentChallenge {

    private int NumberOfPosts;
    private int NumberOfExerciseLogs;
    private boolean isRunning;
    private SelectedFlower selectedFlower;
    Long time;


    public CurrentChallenge() {
    }

    public CurrentChallenge(int numberOfPosts, int numberOfExcerciseLogs, boolean isRunning, SelectedFlower selectedFlower) {
        NumberOfPosts = numberOfPosts;
        NumberOfExerciseLogs = numberOfExcerciseLogs;
        this.isRunning = isRunning;
        this.selectedFlower = selectedFlower;
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
}

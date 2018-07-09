package edu.neu.ccs.wellness.adcaregiverapp.network.services.model;

import com.google.gson.annotations.SerializedName;


public class Challenges {

    @SerializedName("status")
    private ChallengeStatus status;

    @SerializedName("available")
    private AvailableChallenges availableChallenges;

    @SerializedName("running")
    private RunningChallenges runningChallenge;

    public ChallengeStatus getStatus() {
        return status;
    }

    public AvailableChallenges getAvailableChallenges() {
        return availableChallenges;
    }

    public RunningChallenges getRunningChallenge() {
        return runningChallenge;
    }
}

package edu.neu.ccs.wellness.adcaregiverapp.common.utils;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.RunningChallenges;

public class ChallengeManager {


    private SharedPreferences preferences;


    public ChallengeManager(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public void saveRunningChallenge(RunningChallenges runningChallenge) {
        SharedPreferences.Editor editor = preferences.edit();
        String json = new Gson().toJson(runningChallenge);
        editor.putString(Constants.SHARED_PREFS_RUNNING, json);
        editor.apply();
    }

    @Nullable
    public RunningChallenges getRunningChallenge() {
        String runningChallenge = preferences.getString(Constants.SHARED_PREFS_USER, null);
        Gson gson = new Gson();
        return gson.fromJson(runningChallenge, RunningChallenges.class);
    }


}


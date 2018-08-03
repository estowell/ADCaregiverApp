package edu.neu.ccs.wellness.adcaregiverapp.network.services.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.util.Map;

public class LogExerciseModel {


    int userId;
    Long time;

    public LogExerciseModel() {
    }

    public LogExerciseModel(int userId) {
        this.userId = userId;
//        this.time = time;
    }

    public int getUserId() {
        return userId;
    }


    public Map<String, String> getTime() {
        return ServerValue.TIMESTAMP;
    }

    @Exclude
    public Long getTimeLong() {
        return time;
    }

}

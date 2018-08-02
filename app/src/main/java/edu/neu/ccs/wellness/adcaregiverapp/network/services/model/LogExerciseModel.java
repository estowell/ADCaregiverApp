package edu.neu.ccs.wellness.adcaregiverapp.network.services.model;

import java.util.Map;

public class LogExerciseModel {


    int userId;
    Map time;

    public LogExerciseModel() {
    }

    public LogExerciseModel(int userId, Map time) {
        this.userId = userId;
        this.time = time;
    }

}

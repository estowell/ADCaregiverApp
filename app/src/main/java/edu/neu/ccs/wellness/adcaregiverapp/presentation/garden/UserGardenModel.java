package edu.neu.ccs.wellness.adcaregiverapp.presentation.garden;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserGardenModel {

    private String flowerDrawableName;
    private int stage;
    private int position;


    public UserGardenModel() {
    }

    public UserGardenModel(String flowerDrawableName, int stage, int position) {
        this.flowerDrawableName = flowerDrawableName;
        this.stage = stage;
        this.position = position;
    }


    public int getStage() {
        return stage;
    }

    public String getFlowerDrawableName() {
        return flowerDrawableName;
    }

    public int getPosition() {
        return position;
    }
}

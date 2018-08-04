package edu.neu.ccs.wellness.adcaregiverapp.presentation.garden;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserGardenModel {

    private String flowerDrawableName;
    private int position;


    public UserGardenModel() {
    }

    public UserGardenModel(String flowerDrawableName, int position) {
        this.flowerDrawableName = flowerDrawableName;
        this.position = position;
    }

    public String getFlowerDrawableName() {
        return flowerDrawableName;
    }

    public int getPosition() {
        return position;
    }
}

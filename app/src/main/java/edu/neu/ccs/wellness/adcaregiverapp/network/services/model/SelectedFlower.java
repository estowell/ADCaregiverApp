package edu.neu.ccs.wellness.adcaregiverapp.network.services.model;

public class SelectedFlower {

    String name;
    int stage;

    public SelectedFlower() {
    }

    public SelectedFlower(String name, int stage) {
        this.name = name;
        this.stage = stage;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }
}

package edu.neu.ccs.wellness.adcaregiverapp.network.services.model;


import com.google.gson.annotations.SerializedName;

public class UserId {


    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String username;

    public UserId(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

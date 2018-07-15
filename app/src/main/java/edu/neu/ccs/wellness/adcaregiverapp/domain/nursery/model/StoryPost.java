package edu.neu.ccs.wellness.adcaregiverapp.domain.nursery.model;

import edu.neu.ccs.wellness.adcaregiverapp.domain.Model;

public class StoryPost implements Model {

    public String message;

    public int userId;

    public String userName;




    public StoryPost(String message, int userId, String userName) {
        this.message = message;
        this.userId = userId;
        this.userName = userName;
    }
}

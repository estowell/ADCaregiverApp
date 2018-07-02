package edu.neu.ccs.wellness.adcaregiverapp.domain.Nursery.model;

import edu.neu.ccs.wellness.adcaregiverapp.domain.Model;

public class StoryPost implements Model {

    public String message;

    public long count;

    public String userName;

    public StoryPost() {
    }

    public StoryPost(String message, long count, String userName) {
        this.message = message;
        this.count = count;
        this.userName = userName;
    }
}

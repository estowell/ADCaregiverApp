package edu.neu.ccs.wellness.adcaregiverapp.domain.login.model;

import edu.neu.ccs.wellness.adcaregiverapp.domain.Model;

public class User implements Model {

    private Long userId;

    private String username;

    public User(String username) {
        this.username = username;
    }
}

package edu.neu.ccs.wellness.adcaregiverapp.domain.login.model;

import edu.neu.ccs.wellness.adcaregiverapp.domain.Model;
import edu.neu.ccs.wellness.adcaregiverapp.network.server.WellnessUser;

public class User implements Model {

    private WellnessUser user;


    public User(WellnessUser user) {
        this.user = user;
    }
}

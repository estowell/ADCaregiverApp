package edu.neu.ccs.wellness.adcaregiverapp.services.model;

import edu.neu.ccs.wellness.adcaregiverapp.network.services.authorizationService.WellnessUser;

public class LoginResponse {

    public enum LoginStatus {
        SUCCESS, WRONG_CREDENTIALS, NO_INTERNET, IO_ERROR
    }

    private WellnessUser user;


    private LoginStatus reponse;

    public WellnessUser getUser() {
        return user;
    }

    public void setUser(WellnessUser user) {
        this.user = user;
    }

    public LoginStatus getReponse() {
        return reponse;
    }

    public void setReponse(LoginStatus reponse) {
        this.reponse = reponse;
    }
}

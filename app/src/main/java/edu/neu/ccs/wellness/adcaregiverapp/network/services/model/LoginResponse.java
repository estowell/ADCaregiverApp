package edu.neu.ccs.wellness.adcaregiverapp.network.services.model;

import android.support.annotation.Nullable;

import edu.neu.ccs.wellness.adcaregiverapp.domain.login.model.User;

public class LoginResponse {

    public enum LoginStatus {
        SUCCESS, Error
    }

    @Nullable
    private User user;


    private LoginStatus reponse;

    @Nullable
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LoginStatus getReponse() {
        return reponse;
    }

    public void setReponse(LoginStatus reponse) {
        this.reponse = reponse;
    }

    public LoginResponse(@Nullable User user, LoginStatus reponse) {
        this.user = user;
        this.reponse = reponse;
    }
}

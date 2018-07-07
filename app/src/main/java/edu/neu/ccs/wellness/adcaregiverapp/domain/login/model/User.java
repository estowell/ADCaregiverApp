package edu.neu.ccs.wellness.adcaregiverapp.domain.login.model;

import edu.neu.ccs.wellness.adcaregiverapp.domain.Model;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.OauthToken;

public class User implements Model {

    private OauthToken token;

    private String Username;

    private int userId;

    public User(OauthToken token, String username) {
        this.token = token;
        Username = username;
    }

    public User(OauthToken token, String username, int userId) {
        this.token = token;
        Username = username;
        this.userId = userId;
    }

    public OauthToken getToken() {
        return token;
    }

    public void setToken(OauthToken token) {
        this.token = token;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

package edu.neu.ccs.wellness.adcaregiverapp.wellnessLib.models;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("username")
    public String username;

    @SerializedName("password")
    public String password;

    @SerializedName("client_id")
    public String clientid;

    @SerializedName("client_secret")
    public String clientsecret;

    @SerializedName("grant_type")
    public String grant;

    public LoginRequest(String username, String password, String clientid, String clientsecret, String grant) {
        this.username = username;
        this.password = password;
        this.clientid = clientid;
        this.clientsecret = clientsecret;
        this.grant = grant;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getClientsecret() {
        return clientsecret;
    }

    public void setClientsecret(String clientsecret) {
        this.clientsecret = clientsecret;
    }

    public String getGrant() {
        return grant;
    }

    public void setGrant(String grant) {
        this.grant = grant;
    }
}

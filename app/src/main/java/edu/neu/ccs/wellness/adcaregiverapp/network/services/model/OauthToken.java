package edu.neu.ccs.wellness.adcaregiverapp.network.services.model;

import com.google.gson.annotations.SerializedName;

public class OauthToken {
    @SerializedName("access_token")
    private String access_token;

    public String getAccessToken() {
        return this.access_token;
    }

    public void setAccessToken(String access_token) {
        this.access_token = access_token;
    }

    @SerializedName("token_type")
    private String token_type;

    public String getTokenType() {
        return this.token_type;
    }

    public void setTokenType(String token_type) {
        this.token_type = token_type;
    }

    @SerializedName("expires_in")
    private long expires_in;

    public long getExpiresIn() {
        return this.expires_in;
    }

    public void setExpiresIn(long expires_in) {
        this.expires_in = expires_in;
    }

    @SerializedName("refresh_token")
    private String refresh_token;

    public String getRefreshToken() {
        return this.refresh_token;
    }

    public void setRefreshToken(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    @SerializedName("scope")
    private String scope;

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public OauthToken(String access_token, String token_type, long expires_in, String refresh_token, String scope) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.expires_in = expires_in;
        this.refresh_token = refresh_token;
        this.scope = scope;
    }
}

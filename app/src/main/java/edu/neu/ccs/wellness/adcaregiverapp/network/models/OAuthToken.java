package edu.neu.ccs.wellness.adcaregiverapp.network.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import edu.neu.ccs.wellness.adcaregiverapp.network.interfaces.NetworkResponseItem;

/**
 * Created by amritanshtripathi on 6/14/18.
 */

public class OAuthToken implements Parcelable, NetworkResponseItem {

    @SerializedName("access_token")
    public String accessToken;
    @SerializedName("refresh_token")
    public String refreshToken;
    @SerializedName("expiresAt")
    public Long expiresAt;


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Long expiresAt) {
        this.expiresAt = expiresAt;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.accessToken);
        dest.writeString(this.refreshToken);
        dest.writeValue(this.expiresAt);
    }

    public OAuthToken() {
    }

    protected OAuthToken(Parcel in) {
        this.accessToken = in.readString();
        this.refreshToken = in.readString();
        this.expiresAt = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Creator<OAuthToken> CREATOR = new Creator<OAuthToken>() {
        @Override
        public OAuthToken createFromParcel(Parcel source) {
            return new OAuthToken(source);
        }

        @Override
        public OAuthToken[] newArray(int size) {
            return new OAuthToken[size];
        }
    };
}

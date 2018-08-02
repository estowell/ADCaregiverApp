package edu.neu.ccs.wellness.adcaregiverapp.common.utils;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import javax.inject.Inject;

import edu.neu.ccs.wellness.adcaregiverapp.domain.login.model.User;

public class UserManager {

    private SharedPreferences sharedPreferences;

    @Inject
    public UserManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }


    public void saveUser(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(user);
        editor.putString(Constants.SHARED_PREFS_USER, json);
        editor.apply();

    }


    @Nullable
    public User getUser() {
        String userJson = sharedPreferences.getString(Constants.SHARED_PREFS_USER, null);
        Gson gson = new Gson();
        return gson.fromJson(userJson, User.class);
    }

    @Nullable
    public String getToken() {
        User user = getUser();
        if (user != null && user.getToken() != null) {
            return user.getToken().getAccessToken();
        } else {
            return null;
        }
    }


    public String geRefreshtToken() {
        User user = getUser();
        assert user != null;
        return user.getToken().getRefreshToken();
    }

    @Nullable
    public long getTokenExpirationDate() {
        User user = getUser();
        assert user != null;
        return user.getToken().getExpiresIn();

    }

    @Nullable
    public String getAutharizationTokenWithType() {
        String token = getToken();
        if (token != null) {
            return "Bearer " + token;
        } else {
            return null;
        }

    }


    public boolean isTokenExpired() {
        return System.currentTimeMillis() >= this.getTokenExpirationDate();
    }


}

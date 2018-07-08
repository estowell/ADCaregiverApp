package edu.neu.ccs.wellness.adcaregiverapp.repository;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import edu.neu.ccs.wellness.adcaregiverapp.common.utils.Constants;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.OauthToken;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.UserId;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.retrofitInterfaces.UserService;
import retrofit2.Call;


public class UserRepository {

    private UserService service;


    @Inject
    public UserRepository(UserService service) {
        this.service = service;
    }


    public Call<OauthToken> login(@NonNull String username, @NonNull String password) {
        return service.login(Constants.GRANT_TYPE, Constants.CLIENT_ID, Constants.CLIENT_SECRET, username, password);
    }


    public Call<UserId> getUserId(String token) {
        return service.getUserId(token);
    }

    public Call<OauthToken> refreshToken(String refreshToken) {
        return service.refreshToken(Constants.GRANT_TYPE_REFRESH, refreshToken, Constants.CLIENT_ID, Constants.CLIENT_SECRET);
    }
}

package edu.neu.ccs.wellness.adcaregiverapp.repository;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import edu.neu.ccs.wellness.adcaregiverapp.common.utils.Constants;
import edu.neu.ccs.wellness.adcaregiverapp.domain.circles.usecase.GetCircleUseCase;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.Member;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.OauthToken;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.UserCircle;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.UserId;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.retrofitInterfaces.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

    public void getUserCircle(final int userId, final GetCircleUseCase.GetCircleUseCaseCallBack callBack) {
        service.getUserCircle(userId).enqueue(new Callback<UserCircle>() {
            @Override
            public void onResponse(Call<UserCircle> call, Response<UserCircle> response) {
                if (response.isSuccessful()) {
                    UserCircle circle = response.body();
                    List<Member> members = circle.getMembers();
                    for (int i = 0; i < members.size(); i++) {
                        if (userId == members.get(i).getId()) {
                            members.remove(i);
                        }
                    }
                    callBack.onSuccess(members);
                } else {
                    callBack.onError(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<UserCircle> call, Throwable t) {
                callBack.onFailure(t);
            }
        });
    }
}

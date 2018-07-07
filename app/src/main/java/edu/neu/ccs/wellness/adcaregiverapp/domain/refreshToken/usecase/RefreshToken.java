package edu.neu.ccs.wellness.adcaregiverapp.domain.refreshToken.usecase;

import android.support.annotation.Nullable;

import edu.neu.ccs.wellness.adcaregiverapp.common.utils.UserManager;
import edu.neu.ccs.wellness.adcaregiverapp.domain.UseCase;
import edu.neu.ccs.wellness.adcaregiverapp.domain.login.model.User;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.OauthToken;
import edu.neu.ccs.wellness.adcaregiverapp.repository.UserRepository;
import retrofit2.Callback;
import retrofit2.Response;

public class RefreshToken extends UseCase<RefreshToken.RequestValues, RefreshToken.ResponseValue> {

    UserRepository userRepository;
    UserManager userManager;

    public RefreshToken(UseCaseCallback callback, UserRepository userRepository, UserManager userManager) {
        super(callback);
        this.userRepository = userRepository;
        this.userManager = userManager;
    }

    @Override
    public void run() {
        new Call().execute();
    }


    public static final class RequestValues implements UseCase.RequestValues {

    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        @Nullable
        private User user;

        @Nullable
        private String message;


        public ResponseValue(@Nullable User user, @Nullable String message) {
            this.user = user;
            this.message = message;
        }

        @Nullable
        public User getUser() {
            return user;
        }

        @Nullable
        public String getMessage() {
            return message;
        }
    }


    private class Call {

        void execute() {

            userRepository.refreshToken(userManager.geRefreshtToken()).enqueue(new Callback<OauthToken>() {
                @Override
                public void onResponse(retrofit2.Call<OauthToken> call, Response<OauthToken> response) {
                    if (response.isSuccessful()) {

                        User user = userManager.getUser();
                        user.setToken(response.body());
                        userManager.saveUser(user);

                        getUseCaseCallback().onSuccess(new ResponseValue(user, null));
                    } else {
                        userManager.saveUser(null);
                        getUseCaseCallback().onError(new ResponseValue(null, "Error Refreshing the token, Please Login again"));
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<OauthToken> call, Throwable t) {
                    getUseCaseCallback().onFailure();
                }
            });
        }
    }
}

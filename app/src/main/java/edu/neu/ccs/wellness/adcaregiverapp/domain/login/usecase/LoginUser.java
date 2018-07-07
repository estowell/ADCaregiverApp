package edu.neu.ccs.wellness.adcaregiverapp.domain.login.usecase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import edu.neu.ccs.wellness.adcaregiverapp.common.utils.UserManager;
import edu.neu.ccs.wellness.adcaregiverapp.domain.UseCase;
import edu.neu.ccs.wellness.adcaregiverapp.domain.login.model.User;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.LoginResponse;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.OauthToken;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.UserId;
import edu.neu.ccs.wellness.adcaregiverapp.repository.UserRepository;
import retrofit2.Callback;
import retrofit2.Response;

import static edu.neu.ccs.wellness.adcaregiverapp.network.services.model.LoginResponse.LoginStatus;

public class LoginUser extends UseCase<LoginUser.RequestValues, LoginUser.ResponseValue> {

    private UserRepository repository;

    private UserManager userManager;

    public LoginUser(UseCaseCallback callback, UserRepository repository, UserManager userManager) {
        super(callback);
        this.repository = repository;
        this.userManager = userManager;
    }

    public LoginUser(UseCaseCallback callback) {
        super(callback);
    }

    @Override
    public void run() {
        new Call().execute();
    }


    public static final class RequestValues implements UseCase.RequestValues {

        private String username;

        private String password;

        public RequestValues(@NonNull String username, @NonNull String password) {
            this.username = username;

            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

    }


    public static final class ResponseValue implements UseCase.ResponseValue {

        @Nullable
        private LoginResponse response;

        @Nullable
        private String message;


        public ResponseValue(@Nullable LoginResponse response, @Nullable String message) {
            this.response = response;
            this.message = message;
        }

        @Nullable
        public LoginResponse getResponse() {
            return response;
        }

        @Nullable
        public String getMessage() {
            return message;
        }
    }


    private class Call {

        void execute() {
            repository.login(getRequestValues().getUsername(), getRequestValues().getPassword()).enqueue(new Callback<OauthToken>() {
                @Override
                public void onResponse(@NonNull retrofit2.Call<OauthToken> call, @NonNull final Response<OauthToken> response1) {
                    if (response1.isSuccessful()) {
                        repository.getUserId("Bearer " + response1.body().getAccessToken()).enqueue(new Callback<UserId>() {
                            @Override
                            public void onResponse(@NonNull retrofit2.Call<UserId> call, @NonNull Response<UserId> response2) {
                                if (response2.isSuccessful()) {
                                    User user = new User(response1.body(), response2.body().getUsername(), response2.body().getId());
                                    userManager.saveUser(user);
                                    LoginResponse loginResponse = new LoginResponse(user, LoginStatus.SUCCESS);
                                    ResponseValue value = new ResponseValue(loginResponse, null);
                                    getUseCaseCallback().onSuccess(value);
                                } else {

                                    LoginResponse loginResponse = new LoginResponse(null, LoginStatus.Error);
                                    ResponseValue value = new ResponseValue(loginResponse, "Could not fetch userId,Please try again");
                                    getUseCaseCallback().onError(value);
                                }
                            }

                            @Override
                            public void onFailure(retrofit2.Call<UserId> call, Throwable t) {
                                getUseCaseCallback().onFailure();
                                Log.d(this.getClass().getSimpleName(), "Could not fetch userId");
                            }
                        });
                    } else {

                        LoginResponse loginResponse = new LoginResponse(null, LoginStatus.Error);
                        if (response1.code() == 401) {
                            ResponseValue value = new ResponseValue(loginResponse, "Invalid Credentials, Please try again");
                            getUseCaseCallback().onError(value);
                        } else {
                            ResponseValue value = new ResponseValue(loginResponse, "Network Error,Please try again");
                            getUseCaseCallback().onError(value);
                        }

                    }

                }

                @Override
                public void onFailure(retrofit2.Call<OauthToken> call, Throwable t) {
                    getUseCaseCallback().onFailure();
                    Log.d(this.getClass().getSimpleName(), "Could not Login");
                }
            });
        }

    }
}
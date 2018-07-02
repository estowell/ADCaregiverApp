package edu.neu.ccs.wellness.adcaregiverapp.domain.login.usecase;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import edu.neu.ccs.wellness.adcaregiverapp.domain.UseCase;
import edu.neu.ccs.wellness.adcaregiverapp.domain.login.model.User;
import edu.neu.ccs.wellness.adcaregiverapp.repository.UserRepository;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.LoginResponse;

public class LoginUser extends UseCase<LoginUser.RequestValues, LoginUser.ResponseValue, User> {

    private UserRepository repository = new UserRepository();

    public LoginUser(UseCaseCallback callback) {
        super(callback);
    }

    @Override
    public void run() {
        new Async().execute();
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

        public ResponseValue(LoginResponse response, String message) {
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


    private class Async extends AsyncTask<Void, Void, LoginResponse> {

        @Override
        protected LoginResponse doInBackground(Void... voids) {
            return repository.loginUser(getRequestValues().getUsername(), getRequestValues().getPassword());
        }

        @Override
        protected void onPostExecute(LoginResponse loginResponse) {
            if (loginResponse.getReponse() == LoginResponse.LoginStatus.SUCCESS) {
                getUseCaseCallback().onSuccess(new ResponseValue(loginResponse, null));
            } else if (LoginResponse.LoginStatus.WRONG_CREDENTIALS == loginResponse.getReponse()) {
                getUseCaseCallback().onError(new ResponseValue(null, "Please Enter Correct Credentials"));
            } else {
                getUseCaseCallback().onSuccess(new ResponseValue(null, "I/O Error, try again"));
            }
        }
    }
}

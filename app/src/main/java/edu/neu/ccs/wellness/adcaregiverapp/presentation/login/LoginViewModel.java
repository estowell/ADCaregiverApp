package edu.neu.ccs.wellness.adcaregiverapp.presentation.login;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import edu.neu.ccs.wellness.adcaregiverapp.domain.UseCase;
import edu.neu.ccs.wellness.adcaregiverapp.domain.login.usecase.LoginUser;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.LoginResponse;

public class LoginViewModel extends ViewModel {

    private LoginUser loginUser;
    private MutableLiveData<LoginResponse> liveData;

    public MutableLiveData<LoginResponse> getLiveData() {
        if (liveData == null) {
            liveData = new MutableLiveData<>();

        }
        return liveData;
    }

    public void onLogin(@Nullable String username, @Nullable String password) {
        loginUser = new LoginUser(new UseCase.UseCaseCallback<LoginUser.ResponseValue>() {
            @Override
            public void onSuccess(LoginUser.ResponseValue response) {
                liveData.setValue(response.getResponse());
            }

            @Override
            public void onError(LoginUser.ResponseValue response) {
                liveData.setValue(response.getResponse());
            }

        });

        loginUser.setRequestValues(new LoginUser.RequestValues(username, password));
        loginUser.run();
    }
}

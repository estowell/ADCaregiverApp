package edu.neu.ccs.wellness.adcaregiverapp.presentation.login;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import edu.neu.ccs.wellness.adcaregiverapp.common.utils.UserManager;
import edu.neu.ccs.wellness.adcaregiverapp.domain.UseCase;
import edu.neu.ccs.wellness.adcaregiverapp.domain.login.usecase.LoginUser;
import edu.neu.ccs.wellness.adcaregiverapp.repository.UserRepository;

public class LoginViewModel extends ViewModel {

    private UserRepository userRepository;
    private UserManager userManager;

    @Inject
    public LoginViewModel(UserRepository userRepository, UserManager userManager) {
        this.userRepository = userRepository;
        this.userManager = userManager;
    }

    private MutableLiveData<LoginUser.ResponseValue> liveData;

    private MutableLiveData<Boolean> isUserLoggedInLiveData;

    public MutableLiveData<LoginUser.ResponseValue> getLiveData() {
        if (liveData == null) {
            liveData = new MutableLiveData<>();

        }
        return liveData;
    }


    public MutableLiveData<Boolean> getUserLoggedInLiveData() {
        if (isUserLoggedInLiveData == null) {
            isUserLoggedInLiveData = new MutableLiveData<>();
        }
        return isUserLoggedInLiveData;
    }

    public void onLogin(@Nullable String username, @Nullable String password) {
        LoginUser loginUser = new LoginUser(new UseCase.UseCaseCallback<LoginUser.ResponseValue>() {


            @Override
            public void onSuccess(LoginUser.ResponseValue response) {
                liveData.setValue(response);
            }

            @Override
            public void onError(LoginUser.ResponseValue response) {
                liveData.setValue(response);
            }

            @Override
            public void onFailure() {

            }
        }, userRepository, userManager);

        loginUser.setRequestValues(new LoginUser.RequestValues(username, password));
        loginUser.run();
    }


    public void isUserLoggedIn() {
        isUserLoggedInLiveData.setValue(userManager.getUser() != null);
//        final boolean isLoggedIn = userManager.getUser() != null;
//        if (isLoggedIn) {
//            if (userManager.isTokenExpired()) {
//                RefreshToken refreshToken = new RefreshToken(new UseCase.UseCaseCallback<RefreshToken.ResponseValue>() {
//
//
//                    @Override
//                    public void onSuccess(RefreshToken.ResponseValue response) {
//                        if (response.getUser() != null) {
//                            isUserLoggedInLiveData.setValue(true);
//                        }
//                    }
//
//                    @Override
//                    public void onError(RefreshToken.ResponseValue response) {
//                        isUserLoggedInLiveData.setValue(false);
//                    }
//
//                    @Override
//                    public void onFailure() {
//
//                    }
//                }, userRepository, userManager);
//                refreshToken.run();
//            } else {
//                isUserLoggedInLiveData.setValue(true);
//            }
//        } else {
//            isUserLoggedInLiveData.setValue(false);
//        }
    }

}

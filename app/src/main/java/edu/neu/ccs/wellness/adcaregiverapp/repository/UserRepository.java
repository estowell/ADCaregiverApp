package edu.neu.ccs.wellness.adcaregiverapp.repository;

import android.support.annotation.NonNull;

import edu.neu.ccs.wellness.adcaregiverapp.services.NetworkCallBack;
import edu.neu.ccs.wellness.adcaregiverapp.services.UserService;
import edu.neu.ccs.wellness.adcaregiverapp.services.model.LoginResponse;

public class UserRepository {

    private UserService service = new UserService();

    public LoginResponse loginUser(@NonNull String username, @NonNull String password) {
        return service.loginUser(username, password);
    }

    


}

package edu.neu.ccs.wellness.adcaregiverapp.network.services;

import android.support.annotation.Nullable;

import edu.neu.ccs.wellness.adcaregiverapp.network.services.retrofitInterfaces.UserService;

public class UserServiceHolder {

    private UserService service;

    @Nullable
    public UserService getService() {
        return service;
    }

    public void setService(UserService service) {
        this.service = service;
    }
}

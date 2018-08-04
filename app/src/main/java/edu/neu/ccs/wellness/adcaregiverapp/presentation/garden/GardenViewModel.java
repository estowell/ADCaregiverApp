package edu.neu.ccs.wellness.adcaregiverapp.presentation.garden;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

public class GardenViewModel extends ViewModel {


    @Inject
    public GardenViewModel() {
    }

    private MutableLiveData<UserGardenModel> userGardenModelMutableLiveData;

    public MutableLiveData<UserGardenModel> getUserGardenModelMutableLiveData() {

        if (userGardenModelMutableLiveData == null) {
            userGardenModelMutableLiveData = new MutableLiveData();
        }
        return userGardenModelMutableLiveData;

    }




}

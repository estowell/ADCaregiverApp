package edu.neu.ccs.wellness.adcaregiverapp.presentation.challenges;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.AvailableChallenges;
import edu.neu.ccs.wellness.adcaregiverapp.repository.ChallengesRepository;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class ChallengesViewModel extends ViewModel {


    private ChallengesRepository repository;
    private AvailableChallenges availableChallenges;

    @Inject
    public ChallengesViewModel(ChallengesRepository repository) {
        this.repository = repository;
    }

    private MutableLiveData<AvailableChallenges> availableChallengesLiveData;

    private MutableLiveData<String> errorLiveData;

    public MutableLiveData<AvailableChallenges> getAvailableChallengesLiveData() {
        if (availableChallengesLiveData == null) {
            availableChallengesLiveData = new MutableLiveData<>();
        }
        return availableChallengesLiveData;
    }

    public MutableLiveData<String> getErrorLiveData() {
        if (errorLiveData == null) {
            errorLiveData = new MutableLiveData<>();
        }
        return errorLiveData;
    }

    public void fetchAvailableChallenges() {
        repository.getAvailableChallenges(new ChallengeViewModelCallback() {
            @Override
            public void success(Response response) {
                availableChallenges = (AvailableChallenges) response.body();
                availableChallengesLiveData.setValue((AvailableChallenges) response.body());
            }

            @Override
            public void error(ResponseBody error) {
                errorLiveData.setValue("Error getting Challenges");
            }
        });
    }

    public AvailableChallenges getAvailableChallenges() {
        return availableChallenges;
    }

    public interface ChallengeViewModelCallback {
        void success(Response response);

        void error(ResponseBody error);
    }
}

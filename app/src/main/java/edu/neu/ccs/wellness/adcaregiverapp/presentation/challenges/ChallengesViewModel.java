package edu.neu.ccs.wellness.adcaregiverapp.presentation.challenges;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import edu.neu.ccs.wellness.adcaregiverapp.domain.UseCase;
import edu.neu.ccs.wellness.adcaregiverapp.domain.challenge.usecase.AvailableChallengesUseCase;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.AvailableChallenges;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.UnitChallenge;
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

    private MutableLiveData<Boolean> acceptChallengeResponse;

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

    public MutableLiveData<Boolean> getAcceptChallengeResponse() {
        if (acceptChallengeResponse == null) {
            acceptChallengeResponse = new MutableLiveData();
        }
        return acceptChallengeResponse;
    }

    public void fetchAvailableChallenges() {
        AvailableChallengesUseCase availableChallengesUseCase = new AvailableChallengesUseCase(
                new UseCase.UseCaseCallback<AvailableChallengesUseCase.ResponseValues>() {

                    @Override
                    public void onSuccess(AvailableChallengesUseCase.ResponseValues response) {
                        switch (response.getStatus()) {
                            case RUNNING:
                                //doNothing
                                break;
                            case AVAILABLE:
                                availableChallenges = response.getAvailableChallenges();
                                availableChallengesLiveData.setValue(response.getAvailableChallenges());
                                break;
                        }
                    }

                    @Override
                    public void onError(AvailableChallengesUseCase.ResponseValues response) {
                        errorLiveData.setValue("Error getting Challenges");
                    }

                    @Override
                    public void onFailure() {

                    }
                }, repository);
        availableChallengesUseCase.run();
    }

    public AvailableChallenges getAvailableChallenges() {
        return availableChallenges;
    }

    public interface ChallengeViewModelCallback {
        void success(Response response);

        void error(ResponseBody error);
    }


    public void acceptChallenge(UnitChallenge unitChallenge) {
        repository.postAcceptedChallenge(unitChallenge, new ChallengeViewModelCallback() {
            @Override
            public void success(Response response) {
                acceptChallengeResponse.setValue(true);
            }

            @Override
            public void error(ResponseBody error) {
                acceptChallengeResponse.setValue(false);
            }
        });
    }
}

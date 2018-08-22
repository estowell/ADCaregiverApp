package edu.neu.ccs.wellness.adcaregiverapp.presentation.garden;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import edu.neu.ccs.wellness.adcaregiverapp.repository.ChallengesRepository;

public class GardenViewModel extends ViewModel {

    private ChallengesRepository repository;

    @Inject
    public GardenViewModel(ChallengesRepository repository) {
        this.repository = repository;
    }

    private MutableLiveData<GardenViewModelResponse> userGardenModelMutableLiveData;

    public MutableLiveData<GardenViewModelResponse> getUserGardenModelMutableLiveData() {

        if (userGardenModelMutableLiveData == null) {
            userGardenModelMutableLiveData = new MutableLiveData();
        }
        return userGardenModelMutableLiveData;

    }

    public void setChallengeComplete() {
        repository.setChallengeComplete(new GardenViewModelCallBack() {
            @Override
            public void onSuccess() {
                userGardenModelMutableLiveData.setValue(new GardenViewModelResponse(Status.Success));
            }

            @Override
            public void onError(String message) {
                userGardenModelMutableLiveData.setValue(new GardenViewModelResponse(Status.Error, message));
            }
        });
    }

    class GardenViewModelResponse {
        private Status status;
        private String message;

        public GardenViewModelResponse(Status status) {
            this.status = status;
        }

        public GardenViewModelResponse(Status status, String message) {
            this.status = status;
            this.message = message;
        }

        public Status getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }

    public interface GardenViewModelCallBack {

        void onSuccess();

        void onError(String message);
    }

    enum Status {
        Success,
        Error
    }


}

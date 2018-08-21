package edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import edu.neu.ccs.wellness.adcaregiverapp.common.utils.ChallengeManager;
import edu.neu.ccs.wellness.adcaregiverapp.domain.UseCase;
import edu.neu.ccs.wellness.adcaregiverapp.domain.challenge.usecase.GetChallengesUseCase;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.AvailableChallenges;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.PassedChallenge;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.RunningChallenges;
import edu.neu.ccs.wellness.adcaregiverapp.repository.ActivitiesRepository;
import edu.neu.ccs.wellness.adcaregiverapp.repository.ChallengesRepository;

public class NurseryViewModel extends ViewModel {


    private ChallengesRepository challengesRepository;
    private MutableLiveData<NurseryViewModelResponse> responseMutableLiveData;

    @Inject
    public NurseryViewModel(ChallengesRepository challengesRepository, ChallengeManager challengeManager, ActivitiesRepository repository) {
        this.challengesRepository = challengesRepository;
    }


    public MutableLiveData<NurseryViewModelResponse> getResponseMutableLiveData() {
        if (responseMutableLiveData == null) {
            responseMutableLiveData = new MutableLiveData<>();
        }
        return responseMutableLiveData;
    }

    public void getChallenges() {
        GetChallengesUseCase useCase = new GetChallengesUseCase(new UseCase.UseCaseCallback<GetChallengesUseCase.ResponseValues>() {

            @Override
            public void onSuccess(GetChallengesUseCase.ResponseValues response) {
                switch (response.getStatus()) {
                    case AVAILABLE:
                        responseMutableLiveData.setValue(new NurseryViewModelResponse(Status.AVAILABLE, response.getAvailableChallenges()));
                        break;

                    case PASSED:
                        responseMutableLiveData.setValue(new NurseryViewModelResponse(Status.PASSED, response.getPassedChallenge()));
                        break;

                    case RUNNING:
                        responseMutableLiveData.setValue(new NurseryViewModelResponse(Status.RUNNING, response.getRunningChallenge()));
                        break;
                }
            }

            @Override
            public void onError(GetChallengesUseCase.ResponseValues response) {
                responseMutableLiveData.setValue(new NurseryViewModelResponse(Status.ERROR, response.getMessage()));
            }

            @Override
            public void onFailure() {

            }
        }, challengesRepository);
        useCase.run();
    }

    public class NurseryViewModelResponse {
        private Status status;
        private String message;
        private RunningChallenges runningChallenges;
        private AvailableChallenges availableChallenges;
        private PassedChallenge passedChallenge;

        public NurseryViewModelResponse(Status status, String message) {
            this.status = status;
            this.message = message;
        }

        public NurseryViewModelResponse(Status status, AvailableChallenges availableChallenges) {
            this.status = status;
            this.availableChallenges = availableChallenges;
        }

        public NurseryViewModelResponse(Status status, PassedChallenge passedChallenge) {
            this.status = status;
            this.passedChallenge = passedChallenge;
        }

        public NurseryViewModelResponse(Status status, RunningChallenges runningChallenges) {
            this.status = status;
            this.runningChallenges = runningChallenges;
        }

        public Status getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public RunningChallenges getRunningChallenges() {
            return runningChallenges;
        }

        public AvailableChallenges getAvailableChallenges() {
            return availableChallenges;
        }

        public PassedChallenge getPassedChallenge() {
            return passedChallenge;
        }
    }

    enum Status {
        RUNNING,
        AVAILABLE,
        PASSED,
        ERROR
    }
}

package edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import edu.neu.ccs.wellness.adcaregiverapp.common.utils.ChallengeManager;
import edu.neu.ccs.wellness.adcaregiverapp.domain.UseCase;
import edu.neu.ccs.wellness.adcaregiverapp.domain.activities.model.Activities;
import edu.neu.ccs.wellness.adcaregiverapp.domain.activities.usecase.SevenDaysActivityUseCase;
import edu.neu.ccs.wellness.adcaregiverapp.domain.challenge.usecase.GetChallengesUseCase;
import edu.neu.ccs.wellness.adcaregiverapp.domain.challenge.usecase.RunningChallengesUseCase;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.AvailableChallenges;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.PassedChallenge;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.Progress;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.RunningChallenges;
import edu.neu.ccs.wellness.adcaregiverapp.repository.ActivitiesRepository;
import edu.neu.ccs.wellness.adcaregiverapp.repository.ChallengesRepository;

public class NurseryViewModel extends ViewModel {


    private MutableLiveData<Boolean> runningChallengeLiveData;

    private ChallengesRepository challengesRepository;

    private ChallengeManager challengeManager;

    private MutableLiveData<String> errorLiveData;

    private ActivitiesRepository repository;

    private MutableLiveData<StepsResponseObject> stepsLiveData;

    private List<Activities> activities = new ArrayList<>();

    //New
    private MutableLiveData<NurseryViewModelResponse> responseMutableLiveData;

    @Inject
    public NurseryViewModel(ChallengesRepository challengesRepository, ChallengeManager challengeManager, ActivitiesRepository repository) {
        this.challengesRepository = challengesRepository;
        this.challengeManager = challengeManager;
        this.repository = repository;
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
                        responseMutableLiveData.setValue(new NurseryViewModelResponse(Status.PASSED, response.getAvailableChallenges()));
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


    //Old
    public class StepsResponseObject {
        private int todaySteps;
        private int flowerStage;

        public StepsResponseObject(int todaySteps, int flowerStage) {
            this.todaySteps = todaySteps;
            this.flowerStage = flowerStage;
        }

        public int getTodaySteps() {
            return todaySteps;
        }

        public int getFlowerStage() {
            return flowerStage;
        }
    }

    public List<Activities> getActivities() {
        return activities;
    }

    public MutableLiveData<StepsResponseObject> getStepsLiveData() {
        if (stepsLiveData == null) {
            stepsLiveData = new MutableLiveData<>();
        }
        return stepsLiveData;
    }

    public MutableLiveData<String> getErrorLiveData() {
        if (errorLiveData == null) {
            errorLiveData = new MutableLiveData<>();
        }
        return errorLiveData;
    }

    public MutableLiveData<Boolean> getRunningChallengeLiveData() {
        if (runningChallengeLiveData == null) {
            runningChallengeLiveData = new MutableLiveData<>();
        }
        return runningChallengeLiveData;
    }


    public void isChallengeRunning() {
        RunningChallengesUseCase runningChallenge = new RunningChallengesUseCase(new UseCase.UseCaseCallback<RunningChallengesUseCase.ResponseValues>() {

            @Override
            public void onSuccess(RunningChallengesUseCase.ResponseValues response) {
                switch (response.getStatus()) {
                    case RUNNING:
                        runningChallengeLiveData.setValue(true);
                        break;
                    case AVAILABLE:
                        runningChallengeLiveData.setValue(false);
                        break;
                }

            }

            @Override
            public void onError(RunningChallengesUseCase.ResponseValues response) {
                errorLiveData.setValue("Error fetching Running Challenges data");
            }

            @Override
            public void onFailure() {

            }
        }, challengesRepository);
        runningChallenge.run();
    }


    public void getSevenDaysActivity() {
        SevenDaysActivityUseCase useCase = new SevenDaysActivityUseCase(new UseCase.UseCaseCallback<SevenDaysActivityUseCase.ResponseValues>() {

            @Override
            public void onSuccess(SevenDaysActivityUseCase.ResponseValues response) {
                int stepCount = 0;
                activities = response.getActivities();


                for (Activities activities : response.getActivities())
                    if (new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(activities.getDate())
                            .equals(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()))) {
                        stepCount = activities.getSteps();
                    }

                Progress progress = Objects.requireNonNull(challengeManager.getRunningChallenge()).getProgress().get(0);

                if (stepCount > progress.getGoal())
                    stepsLiveData.setValue(new StepsResponseObject(100, 1));
                else {
                    int stepPercentage = (stepCount / progress.getGoal()) * 100;
                    stepsLiveData.setValue(new StepsResponseObject(stepPercentage, 1));
                }
            }

            @Override
            public void onError(SevenDaysActivityUseCase.ResponseValues response) {
                errorLiveData.setValue("Error retrieving steps");
            }

            @Override
            public void onFailure() {

            }
        }, repository);

        useCase.setRequestValues(new SevenDaysActivityUseCase.RequestValues(challengeManager.getRunningChallenge().getStartDatetime()));
        useCase.run();
    }

}

package edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import edu.neu.ccs.wellness.adcaregiverapp.common.utils.ChallengeManager;
import edu.neu.ccs.wellness.adcaregiverapp.domain.UseCase;
import edu.neu.ccs.wellness.adcaregiverapp.domain.activities.model.Activities;
import edu.neu.ccs.wellness.adcaregiverapp.domain.activities.usecase.SevenDaysActivityUseCase;
import edu.neu.ccs.wellness.adcaregiverapp.domain.challenge.usecase.RunningChallengesUseCase;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.Progress;
import edu.neu.ccs.wellness.adcaregiverapp.repository.ActivitiesRepository;
import edu.neu.ccs.wellness.adcaregiverapp.repository.ChallengesRepository;

public class NurseryViewModel extends ViewModel {


    private MutableLiveData<Boolean> runningChallengeLiveData;

    private ChallengesRepository challengesRepository;

    private ChallengeManager challengeManager;

    private MutableLiveData<String> errorLiveData;

    private ActivitiesRepository repository;

    private MutableLiveData<Integer> stepsLiveData;

    private List<Activities> activities = new ArrayList<>();

    @Inject
    public NurseryViewModel(ChallengesRepository challengesRepository, ChallengeManager challengeManager, ActivitiesRepository repository) {
        this.challengesRepository = challengesRepository;
        this.challengeManager = challengeManager;
        this.repository = repository;
    }


    public List<Activities> getActivities() {
        return activities;
    }

    public MutableLiveData<Integer> getStepsLiveData() {
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
                    stepCount += activities.getSteps();

                Progress progress = Objects.requireNonNull(challengeManager.getRunningChallenge()).getProgress().get(0);

                if (stepCount > progress.getGoal())
                    stepsLiveData.setValue(100);
                else {
                    int stepPercentage = (stepCount / progress.getGoal()) * 100;
                    stepsLiveData.setValue(stepPercentage);
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


    //TODO: Clean ME
//    private ShareStory shareStory;
//    private MutableLiveData<Firebase.Status> sharePost;

//    public MutableLiveData<Firebase.Status> getSharePost() {
//        return sharePost;
//    }
//
//
//
//    public void sharePost(@NonNull String userName, @NonNull String message, @NonNull int userId) {
//        shareStory = new ShareStory(new UseCase.UseCaseCallback<ShareStory.ResponseValue>() {
//            @Override
//            public void onSuccess(ShareStory.ResponseValue response) {
//                sharePost.setValue(response.getStatus());
//            }
//
//            @Override
//            public void onError(ShareStory.ResponseValue response) {
//                sharePost.setValue(response.getStatus());
//            }
//
//            @Override
//            public void onFailure() {
//
//            }
//        });
//    }


}

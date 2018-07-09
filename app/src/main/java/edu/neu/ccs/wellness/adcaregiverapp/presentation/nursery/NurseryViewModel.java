package edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import edu.neu.ccs.wellness.adcaregiverapp.domain.Challenge.usecase.RunningChallengesUseCase;
import edu.neu.ccs.wellness.adcaregiverapp.domain.UseCase;
import edu.neu.ccs.wellness.adcaregiverapp.repository.ChallengesRepository;

public class NurseryViewModel extends ViewModel {


    private MutableLiveData<Boolean> runningChallengeLivedata;

    private ChallengesRepository challengesRepository;

    private MutableLiveData<String> errorLiveData;

    @Inject
    public NurseryViewModel(ChallengesRepository challengesRepository) {
        this.challengesRepository = challengesRepository;
    }

    public MutableLiveData<String> getErrorLiveData() {
        if (errorLiveData == null) {
            errorLiveData = new MutableLiveData<>();
        }
        return errorLiveData;
    }

    public MutableLiveData<Boolean> getRunningChallengeLivedata() {
        if (runningChallengeLivedata == null) {
            runningChallengeLivedata = new MutableLiveData<>();
        }
        return runningChallengeLivedata;
    }

    public void isChallengeRunning() {
        RunningChallengesUseCase runningChallenge = new RunningChallengesUseCase(new UseCase.UseCaseCallback<RunningChallengesUseCase.ResponseValues>() {

            @Override
            public void onSuccess(RunningChallengesUseCase.ResponseValues response) {
                switch (response.getStatus()) {
                    case RUNNING:
                        runningChallengeLivedata.setValue(true);
                        break;
                    case AVAILABLE:
                        runningChallengeLivedata.setValue(false);
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

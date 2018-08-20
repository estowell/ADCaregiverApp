package edu.neu.ccs.wellness.adcaregiverapp.repository;

import java.util.Calendar;

import javax.inject.Inject;

import edu.neu.ccs.wellness.adcaregiverapp.common.utils.ChallengeManager;
import edu.neu.ccs.wellness.adcaregiverapp.domain.challenge.usecase.AvailableChallengesUseCase.AvailableChallengesCallBack;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.Challenges;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.PassedChallenge;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.RunningChallenges;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.UnitChallenge;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.retrofitInterfaces.ChallengeServicesInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static edu.neu.ccs.wellness.adcaregiverapp.presentation.challenges.ChallengesViewModel.ChallengeViewModelCallback;

public class ChallengesRepository {


    private ChallengeServicesInterface challengeService;
    private ChallengeManager challengeManager;

    @Inject
    public ChallengesRepository(Retrofit retrofit, ChallengeManager challengeManager) {
        challengeService = retrofit.create(ChallengeServicesInterface.class);
        this.challengeManager = challengeManager;
    }

    public void getRunningChallenge(AvailableChallengesCallBack callback) {
        RunningChallenges runningChallenge = challengeManager.getRunningChallenge();
        if (runningChallenge != null && runningChallenge.getIsCurrentlyRunning() && runningChallenge.getEndDatetime().after(Calendar.getInstance().getTime())) {
            callback.running(runningChallenge);
        } else {
            getChallenges(callback);
        }
    }

    public void getChallenges(final AvailableChallengesCallBack callback) {

        Call<Challenges> call = challengeService.getChallenges();

        call.enqueue(new Callback<Challenges>() {
            @Override
            public void onResponse(Call<Challenges> call, Response<Challenges> response) {
                if (response.errorBody() == null) {
                    Challenges challenges = response.body();
                    switch (challenges.getStatus()) {
                        case AVAILABLE:
                            callback.available(challenges.getAvailableChallenges());
                            break;
                        case RUNNING:
                            RunningChallenges challenge = challenges.getRunningChallenge();
                            challengeManager.saveRunningChallenge(challenge);
                            callback.running(challenge);
                            break;
                        case PASSED:
                            PassedChallenge passedChallenge = challenges.getPassedChallenge();
                            callback.passed(passedChallenge);
                            break;
                    }
                } else {
                    callback.error(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Challenges> call, Throwable t) {
                callback.failure(t);
            }
        });
    }


    public void postAcceptedChallenge(UnitChallenge unitChallenge, final ChallengeViewModelCallback viewModelCallback) {
        Call<RunningChallenges> call = challengeService.postAcceptedChallenge(unitChallenge);

        call.enqueue(new Callback<RunningChallenges>() {
            @Override
            public void onResponse(Call<RunningChallenges> call, Response<RunningChallenges> response) {
                if (response.errorBody() == null) {
                    viewModelCallback.success(response);
                } else {
                    viewModelCallback.error(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<RunningChallenges> call, Throwable t) {

            }
        });
    }

}

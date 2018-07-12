package edu.neu.ccs.wellness.adcaregiverapp.domain.challenge.usecase;

import android.support.annotation.Nullable;

import edu.neu.ccs.wellness.adcaregiverapp.domain.UseCase;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.AvailableChallenges;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.ChallengeStatus;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.RunningChallenges;
import edu.neu.ccs.wellness.adcaregiverapp.repository.ChallengesRepository;
import okhttp3.ResponseBody;

public class RunningChallengesUseCase extends UseCase<UseCase.RequestValues, UseCase.ResponseValue> {


    ChallengesRepository repository;

    public RunningChallengesUseCase(UseCaseCallback callback, ChallengesRepository repository) {
        super(callback);
        this.repository = repository;
    }

    @Override
    public void run() {
        new Call().execute();
    }

    public class RequestValues implements UseCase.RequestValues {

    }

    public class ResponseValues implements ResponseValue {

        private ChallengeStatus status;
        private RunningChallenges runningChallenge;

        public ResponseValues(ChallengeStatus status, @Nullable RunningChallenges runningChallenge) {
            this.status = status;
            this.runningChallenge = runningChallenge;
        }

        public ChallengeStatus getStatus() {
            return status;
        }

        @Nullable
        public RunningChallenges getRunningChallenge() {
            return runningChallenge;
        }
    }

    private class Call {

        public void execute() {

            repository.getChallenges(new AvailableChallengesUseCase.AvailableChallengesCallBack() {
                @Override
                public void available(AvailableChallenges availableChallenges) {
                    getUseCaseCallback().onSuccess(new RunningChallengesUseCase.ResponseValues(ChallengeStatus.AVAILABLE, null));
                }

                @Override
                public void running(RunningChallenges runningChallenge) {
                    getUseCaseCallback().onSuccess(new RunningChallengesUseCase.ResponseValues(ChallengeStatus.RUNNING, runningChallenge));
                }

                @Override
                public void error(ResponseBody error) {
                    getUseCaseCallback().onError(null);
                }

                @Override
                public void failure(Throwable t) {

                }
            });
        }
    }

}

package edu.neu.ccs.wellness.adcaregiverapp.domain.Challenge.usecase;

import android.support.annotation.Nullable;

import edu.neu.ccs.wellness.adcaregiverapp.domain.UseCase;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.AvailableChallenges;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.ChallengeStatus;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.RunningChallenges;
import edu.neu.ccs.wellness.adcaregiverapp.repository.ChallengesRepository;
import okhttp3.ResponseBody;

public class AvailableChallengesUseCase extends UseCase<UseCase.RequestValues, UseCase.ResponseValue> {

    ChallengesRepository repository;

    public AvailableChallengesUseCase(UseCaseCallback callback, ChallengesRepository repository) {
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
        private AvailableChallenges availableChallenges;

        public ResponseValues(ChallengeStatus status, @Nullable AvailableChallenges availableChallenges) {
            this.status = status;
            this.availableChallenges = availableChallenges;
        }

        public ChallengeStatus getStatus() {
            return status;
        }

        @Nullable
        public AvailableChallenges getAvailableChallenges() {
            return availableChallenges;
        }
    }

    private class Call {

        public void execute() {
            repository.getChallenges(new AvailableChallengesCallBack() {
                @Override
                public void available(AvailableChallenges availableChallenges) {
                    getUseCaseCallback().onSuccess(new ResponseValues(ChallengeStatus.AVAILABLE, availableChallenges));
                }

                @Override
                public void running(RunningChallenges runningChallenge) {
                    getUseCaseCallback().onSuccess(new ResponseValues(ChallengeStatus.RUNNING, null));
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

    public interface AvailableChallengesCallBack {
        void available(AvailableChallenges availableChallenges);

        void running(RunningChallenges runningChallenge);

        void error(ResponseBody error);

        void failure(Throwable t);
    }
}

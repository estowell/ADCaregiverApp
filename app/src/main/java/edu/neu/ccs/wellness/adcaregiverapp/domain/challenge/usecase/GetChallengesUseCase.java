package edu.neu.ccs.wellness.adcaregiverapp.domain.challenge.usecase;

import android.support.annotation.Nullable;

import edu.neu.ccs.wellness.adcaregiverapp.domain.UseCase;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.AvailableChallenges;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.ChallengeStatus;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.PassedChallenge;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.RunningChallenges;
import edu.neu.ccs.wellness.adcaregiverapp.repository.ChallengesRepository;
import okhttp3.ResponseBody;


public class GetChallengesUseCase extends UseCase<UseCase.RequestValues, UseCase.ResponseValue> {


    ChallengesRepository repository;

    public GetChallengesUseCase(UseCaseCallback callback, ChallengesRepository repository) {
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
        private AvailableChallenges availableChallenges;
        private PassedChallenge passedChallenge;
        private String message;


        public ResponseValues(ChallengeStatus status, @Nullable RunningChallenges runningChallenge) {
            this.status = status;
            this.runningChallenge = runningChallenge;
        }

        public ResponseValues(ChallengeStatus status, AvailableChallenges availableChallenges) {
            this.status = status;
            this.availableChallenges = availableChallenges;
        }

        public ResponseValues(ChallengeStatus status, PassedChallenge passedChallenge) {
            this.status = status;
            this.passedChallenge = passedChallenge;
        }

        public AvailableChallenges getAvailableChallenges() {
            return availableChallenges;
        }

        public PassedChallenge getPassedChallenge() {
            return passedChallenge;
        }

        public ResponseValues(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
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
                    getUseCaseCallback().onSuccess(new GetChallengesUseCase.ResponseValues(ChallengeStatus.AVAILABLE, availableChallenges));
                }

                @Override
                public void running(RunningChallenges runningChallenge) {
                    getUseCaseCallback().onSuccess(new GetChallengesUseCase.ResponseValues(ChallengeStatus.RUNNING, runningChallenge));
                }

                @Override
                public void passed(PassedChallenge passed) {
                    getUseCaseCallback().onSuccess(new GetChallengesUseCase.ResponseValues(ChallengeStatus.AVAILABLE, passed));
                }

                @Override
                public void error(ResponseBody error) {
                    getUseCaseCallback().onError(new ResponseValues("Error fetching challenges"));
                }

                @Override
                public void failure(Throwable t) {
                    getUseCaseCallback().onError(new ResponseValues(t.getMessage()));
                }
            });
        }
    }

    public interface GetChallengesCallBack {
        void available(AvailableChallenges availableChallenges);

        void running(RunningChallenges runningChallenge);

        void passed(PassedChallenge passed);

        void error(ResponseBody error);

        void failure(Throwable t);
    }

}
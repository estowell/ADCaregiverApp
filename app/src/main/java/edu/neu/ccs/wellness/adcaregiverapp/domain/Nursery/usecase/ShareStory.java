package edu.neu.ccs.wellness.adcaregiverapp.domain.Nursery.usecase;

import edu.neu.ccs.wellness.adcaregiverapp.domain.Nursery.model.StoryPost;
import edu.neu.ccs.wellness.adcaregiverapp.domain.UseCase;
import edu.neu.ccs.wellness.adcaregiverapp.repository.NurseryRepository;

import static edu.neu.ccs.wellness.adcaregiverapp.network.services.Firebase.Status;

public class ShareStory extends UseCase<ShareStory.RequestValues, ShareStory.ResponseValue> {


    private NurseryRepository repository = new NurseryRepository();

    public ShareStory(UseCaseCallback callback) {
        super(callback);
    }

    @Override
    public void run() {
        new Async().execute();
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private String message;

        private int userId;

        private String userName;

        public RequestValues(String message, int userId, String userName) {
            this.message = message;
            this.userId = userId;
            this.userName = userName;
        }

        public String getMessage() {
            return message;
        }

        public int getUserId() {
            return userId;
        }

        public String getUserName() {
            return userName;
        }
    }


    public static final class ResponseValue implements UseCase.ResponseValue {


        Status status;

        public ResponseValue(Status status) {
            this.status = status;
        }

        public Status getStatus() {
            return status;
        }
    }


    private class Async {

        void execute() {
            repository.postStory(new StoryPost(getRequestValues().message, getRequestValues().userId, getRequestValues().userName), new NurseryRepository.FireBaseCallBack() {
                @Override
                public Status callBackMethod(Status status) {
                    switch (status) {
                        case SUCCESS:
                            getUseCaseCallback().onSuccess(new ResponseValue(status));
                            break;

                        case ERROR:
                            getUseCaseCallback().onError(new ResponseValue(status));
                    }
                    return status;
                }
            });
        }

    }


}

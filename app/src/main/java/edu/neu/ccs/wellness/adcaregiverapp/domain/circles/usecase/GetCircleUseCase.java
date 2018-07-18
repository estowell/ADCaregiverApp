package edu.neu.ccs.wellness.adcaregiverapp.domain.circles.usecase;

import java.util.List;

import edu.neu.ccs.wellness.adcaregiverapp.domain.UseCase;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.Member;
import edu.neu.ccs.wellness.adcaregiverapp.repository.UserRepository;
import okhttp3.ResponseBody;

public class GetCircleUseCase extends UseCase<GetCircleUseCase.Request, GetCircleUseCase.Response> {


    UserRepository repository;

    public GetCircleUseCase(UseCaseCallback callback, UserRepository repository) {
        super(callback);
        this.repository = repository;
    }


    @Override
    public void run() {
        new Run().execute();
    }

    public static class Response implements UseCase.ResponseValue {
        private List<Member> members;

        public Response(List<Member> members) {
            this.members = members;
        }

        public List<Member> getMembers() {
            return members;
        }
    }

    public static class Request implements UseCase.RequestValues {
        private int userId;

        public Request(int userId) {
            this.userId = userId;
        }

        public int getUserId() {
            return userId;
        }
    }


    private class Run {
        public void execute() {
            repository.getUserCircle(getRequestValues().getUserId(), new GetCircleUseCaseCallBack() {
                @Override
                public void onSuccess(List<Member> members) {
                    getUseCaseCallback().onSuccess(new Response(members));
                }

                @Override
                public void onError(ResponseBody body) {

                    getUseCaseCallback().onError(null);
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
    }

    public interface GetCircleUseCaseCallBack {
        void onSuccess(List<Member> members);

        void onError(ResponseBody body);

        void onFailure(Throwable t);
    }
}

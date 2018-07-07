package edu.neu.ccs.wellness.adcaregiverapp.repository;

public abstract class Repository {

    private RepositoryCallBack callBack;

    public Repository(RepositoryCallBack callBack) {
        this.callBack = callBack;
    }

    public RepositoryCallBack getCallBack() {
        return callBack;
    }

    public interface RepositoryCallBack<R> {
        void onSuccess(R response);

        void onError(R response);

        void onFailure();
    }

}

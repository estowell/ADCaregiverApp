package edu.neu.ccs.wellness.adcaregiverapp.domain;

import android.os.AsyncTask;

public abstract class UseCase<Q extends UseCase.RequestValues, P extends UseCase.ResponseValue> {

    private UseCaseCallback callback;

    private AsyncTask asyncTask;


    public UseCase(UseCaseCallback callback) {
        this.callback = callback;
    }

    public void setAsyncTask(AsyncTask asyncTask) {
        this.asyncTask = asyncTask;
    }

    private Q mRequestValues;

    private P mResponseValues;

    public P getmResponseValues() {
        return mResponseValues;
    }

    public void setmResponseValues(P mResponseValues) {
        this.mResponseValues = mResponseValues;
    }

    public void setRequestValues(Q requestValues) {
        mRequestValues = requestValues;
    }

    public Q getRequestValues() {
        return mRequestValues;
    }

    public UseCaseCallback<P> getUseCaseCallback() {
        return callback;
    }

    public void setUseCaseCallback(UseCaseCallback<P> useCaseCallback) {
        callback = useCaseCallback;
    }

    public abstract void run();


    /**
     * Data passed to a request.
     */
    public interface RequestValues {
    }

    /**
     * Data received from a request.
     */
    public interface ResponseValue {
    }

    public interface UseCaseCallback<R> {
        void onSuccess(R response);

        void onError(R response);

        void onFailure();
    }

}

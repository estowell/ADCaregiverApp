package edu.neu.ccs.wellness.adcaregiverapp.services;

public interface NetworkCallBack<T> {

    public void onSucess(T response);

    public void onError(T response);
}

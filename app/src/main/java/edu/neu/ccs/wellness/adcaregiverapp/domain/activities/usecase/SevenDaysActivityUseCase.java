package edu.neu.ccs.wellness.adcaregiverapp.domain.activities.usecase;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.neu.ccs.wellness.adcaregiverapp.domain.UseCase;
import edu.neu.ccs.wellness.adcaregiverapp.domain.activities.model.Activities;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.UserActivities;
import edu.neu.ccs.wellness.adcaregiverapp.repository.ActivitiesRepository;
import okhttp3.ResponseBody;

public class SevenDaysActivityUseCase extends UseCase<SevenDaysActivityUseCase.RequestValues, SevenDaysActivityUseCase.ResponseValues> {

    ActivitiesRepository repository;

    public SevenDaysActivityUseCase(UseCaseCallback callback, ActivitiesRepository repository) {
        super(callback);
        this.repository = repository;
    }

    @Override
    public void run() {
        new Call().execute();
    }


    public static class RequestValues implements UseCase.RequestValues {

        private Date date;

        public RequestValues(@NonNull Date date) {
            this.date = date;
        }

        @NonNull
        public Date getDate() {
            return date;
        }
    }

    public class ResponseValues implements UseCase.ResponseValue {

        private List<Activities> activities;

        public ResponseValues(List<Activities> activities) {
            this.activities = activities;
        }

        public List<Activities> getActivities() {
            return activities;
        }
    }

    private class Call {

        public void execute() {
            String startDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(getRequestValues().getDate());
            startDate = "2017-06-01";
            repository.getSevenDaysActivities(startDate, new SevenDaysActivitiesCallBack() {
                @Override
                public void onSuccess(List<UserActivities> activities) {
                    List<Activities> responses = new ArrayList<>();
                    for (UserActivities userActivity : activities) {
                        try {
                            @SuppressLint("SimpleDateFormat")
                            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(userActivity.getDate());
                            Activities response = new Activities(date, userActivity.getSteps());
                            responses.add(response);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    getUseCaseCallback().onSuccess(new ResponseValues(responses));
                }

                @Override
                public void onError(ResponseBody body) {
                    getUseCaseCallback().onError(new ResponseValues(null));
                }

                @Override
                public void onFailure(Throwable t) {
                    getUseCaseCallback().onFailure();
                }
            });
        }
    }


    public interface SevenDaysActivitiesCallBack {
        void onSuccess(List<UserActivities> activities);

        void onError(ResponseBody body);

        void onFailure(Throwable t);
    }
}

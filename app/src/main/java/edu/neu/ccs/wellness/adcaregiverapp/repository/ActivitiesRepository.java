package edu.neu.ccs.wellness.adcaregiverapp.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import edu.neu.ccs.wellness.adcaregiverapp.common.utils.UserManager;
import edu.neu.ccs.wellness.adcaregiverapp.domain.activities.usecase.SevenDaysActivityUseCase;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.GroupActivities;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.SevenDaysActivityResponse;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.UserActivities;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.retrofitInterfaces.ActivitiesServiceInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ActivitiesRepository {

    private ActivitiesServiceInterface service;
    private UserManager userManager;

    @Inject
    public ActivitiesRepository(Retrofit retrofit, UserManager userManager) {

        this.userManager = userManager;
        this.service = retrofit.create(ActivitiesServiceInterface.class);
    }

    public void getSevenDaysActivities(String startDate, final SevenDaysActivityUseCase.SevenDaysActivitiesCallBack callBack) {
        service.getSevenDaysActivit(startDate).enqueue(new Callback<SevenDaysActivityResponse>() {
            @Override
            public void onResponse(Call<SevenDaysActivityResponse> call, Response<SevenDaysActivityResponse> response) {
                if (response.isSuccessful()) {
                    List<UserActivities> activities = new ArrayList<>();
                    assert response.body() != null;
                    for (GroupActivities groupActivities : response.body().getActivities()) {
                        if (Objects.requireNonNull(userManager.getUser()).getUserId() == groupActivities.getId()) {

                            activities.addAll(groupActivities.getActivities());

                        }
                    }

                    callBack.onSuccess(activities);

                } else {
                    callBack.onError(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<SevenDaysActivityResponse> call, Throwable t) {
                callBack.onFailure(t);
            }
        });
    }


}

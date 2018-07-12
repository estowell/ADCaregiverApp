package edu.neu.ccs.wellness.adcaregiverapp.network.services.retrofitInterfaces;

import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.SevenDaysActivityResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ActivitiesServiceInterface {

    @GET("api/group/activities/7d/{date}")
    Call<SevenDaysActivityResponse> getSevenDaysActivit(@Path(value = "date") String date);
}

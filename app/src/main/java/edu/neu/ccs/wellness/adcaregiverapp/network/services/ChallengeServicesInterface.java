package edu.neu.ccs.wellness.adcaregiverapp.network.services;

import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.AvailableChallenges;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ChallengeServicesInterface {


    @GET("api/group/challenges/available")
    Call<AvailableChallenges> getAvailableChallenges(@Header("Authorization") String token);

}

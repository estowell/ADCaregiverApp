package edu.neu.ccs.wellness.adcaregiverapp.network.services.retrofitInterfaces;

import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.AvailableChallenges;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ChallengeServicesInterface {


    @GET("api/group/challenges/available")
    Call<AvailableChallenges> getAvailableChallenges();

}

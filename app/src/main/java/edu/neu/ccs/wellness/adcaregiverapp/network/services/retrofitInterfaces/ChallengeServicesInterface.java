package edu.neu.ccs.wellness.adcaregiverapp.network.services.retrofitInterfaces;

import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.Challenges;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.RunningChallenges;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.UnitChallenge;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ChallengeServicesInterface {


    @GET("api/group/challenges")
    Call<Challenges> getChallenges();


    @POST("api/group/challenges")
    Call<RunningChallenges> postAcceptedChallenge(@Body UnitChallenge unitChallenge);

    @GET("api/group/challenges/set_completed")
    Call<Void> setChallengeComplete();
}

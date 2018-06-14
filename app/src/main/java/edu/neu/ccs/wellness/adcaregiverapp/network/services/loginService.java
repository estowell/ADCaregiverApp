package edu.neu.ccs.wellness.adcaregiverapp.network.services;

import edu.neu.ccs.wellness.adcaregiverapp.network.models.OAuthToken;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;

/**
 * Created by amritanshtripathi on 6/14/18.
 */

interface loginService {

    String BASE_URL = "http://wellness.ccs.neu.edu/storytelling_dev/";

    @POST("oauth/token")
    Call<OAuthToken> login(@Field("username") String username, @Field("password") String password,
                           @Field("client_id") String clientID, @Field("client_secret") String clientSecret);

}

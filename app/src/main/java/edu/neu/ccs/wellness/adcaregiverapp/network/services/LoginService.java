package edu.neu.ccs.wellness.adcaregiverapp.network.services;

import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.OauthToken;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.UserId;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginService {

    @FormUrlEncoded
    @POST("oauth/token/")
    Call<OauthToken> login(@Field("grant_type") String grantType,
                           @Field("client_id") String clientId, @Field("client_secret") String clientSecret, @Field("username") String username,
                           @Field("password") String password);


    @GET("api/person/info/")
    Call<UserId> getUserId(@Header("Authorization") String token);
}

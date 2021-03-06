package edu.neu.ccs.wellness.adcaregiverapp.network.services.retrofitInterfaces;

import java.util.List;

import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.OauthToken;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.UserCircle;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.UserId;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {

    @FormUrlEncoded
    @POST("oauth/token/")
    Call<OauthToken> login(@Field("grant_type") String grantType,
                           @Field("client_id") String clientId, @Field("client_secret") String clientSecret, @Field("username") String username,
                           @Field("password") String password);


    @GET("api/person/info/")
    Call<UserId> getUserId(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("oauth/token/")
    Call<OauthToken> refreshToken(@Field("grant_type") String grantType, @Field("refresh_token") String refreshToken,
                                  @Field("client_id") String clientId, @Field("client_secret") String clientSecret);

    @GET("api/circle/all/")
    Call<List<UserCircle>> getUserCircle();
}

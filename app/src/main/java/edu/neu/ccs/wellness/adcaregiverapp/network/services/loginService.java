package edu.neu.ccs.wellness.adcaregiverapp.network.services;

import edu.neu.ccs.wellness.adcaregiverapp.network.models.OAuthToken;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by amritanshtripathi on 6/14/18.
 */

public interface loginService {


    @POST("adc_dev/oauth/token")
    Call<OAuthToken> login(@Header("Content-Type") String contentType, @Body RequestBody loginRequest);

}

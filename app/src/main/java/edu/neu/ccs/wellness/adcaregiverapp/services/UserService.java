package edu.neu.ccs.wellness.adcaregiverapp.services;

import android.support.annotation.NonNull;

import java.io.IOException;

import edu.neu.ccs.wellness.adcaregiverapp.network.services.authorizationService.OAuth2Exception;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.authorizationService.WellnessUser;
import edu.neu.ccs.wellness.adcaregiverapp.services.model.LoginResponse;

public class UserService {


    private final String client_id = "IYWMhbU1NCBF7o0Putz5C52EnzmFaz2Nz5BDCNAn";
    private final String client_secret = "OIOftryscsaETXUb6amf5DPA8ewpXYX0vpSDtzftljyBi0cbLAkOj8C5uIz5xIpOzLugHvZQNcs7AS4MTpBUJr2QYPC733yQ2e8LWzLbdELJiAZu77PXy9O6qcUxbdIc";
    private static final String SERVER_URL = "http://wellness.ccs.neu.edu/adc_dev/";
    private static final String API_PATH = "api/";
    private static final String OAUTH_TOKEN_PATH = "oauth/token/";



    public LoginResponse loginUser(@NonNull final String username, @NonNull final String password) {

        LoginResponse response = new LoginResponse();
        try {
            WellnessUser user = new WellnessUser(username, password, client_id, client_secret, SERVER_URL, OAUTH_TOKEN_PATH);
            response.setUser(user);
            response.setReponse(LoginResponse.LoginStatus.SUCCESS);

        } catch (OAuth2Exception e) {
            response.setReponse(LoginResponse.LoginStatus.WRONG_CREDENTIALS);
            e.printStackTrace();
        } catch (IOException e) {
            response.setReponse(LoginResponse.LoginStatus.IO_ERROR);
            e.printStackTrace();
        }
        return response;

    }


}



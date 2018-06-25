package edu.neu.ccs.wellness.adcaregiverapp.presentation;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.databinding.ActivityMainBinding;
import edu.neu.ccs.wellness.adcaregiverapp.domain.UseCase;
import edu.neu.ccs.wellness.adcaregiverapp.domain.login.usecase.LoginUser;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.communityGarden.CommunityGardenFragment;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery.NurseryFragment;
import retrofit2.Retrofit;

public class MainActivity extends DaggerAppCompatActivity {

    @Inject
    Retrofit api;

    private final String baseurl = "http://wellness.ccs.neu.edu";
    private final String DEFAULT_USERNAME = "anna";
    private final String DEFAULT_PASSWORD = "tacos000";
    private final String client_id = "IYWMhbU1NCBF7o0Putz5C52EnzmFaz2Nz5BDCNAn";
    private final String client_secret = "OIOftryscsaETXUb6amf5DPA8ewpXYX0vpSDtzftljyBi0cbLAkOj8C5uIz5xIpOzLugHvZQNcs7AS4MTpBUJr2QYPC733yQ2e8LWzLbdELJiAZu77PXy9O6qcUxbdIc";
    public static final String SERVER_URL = "http://wellness.ccs.neu.edu/adc_dev/";
    public static final String API_PATH = "api/";
    public static final String OAUTH_TOKEN_PATH = "oauth/token/";


    private enum CurrentTab {
        NURSERY,
        MY_GARDEN,
        COMMUNITY_GARDEN
    }

    private CurrentTab selectedTab = CurrentTab.COMMUNITY_GARDEN;


    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        init();
    }


    private void init() {

        //Bottom Navigation Logic
        binding.communityGarden.setTextColor(getResources().getColor(R.color.white));
        binding.communityGarden.setBackgroundColor(getResources().getColor(R.color.bottom_navigation_color));

        setBottomNavigationOnClickListener();

        navigateToCommunityGarden();

        //TODO: CleanME

//        LoginRequest request = new LoginRequest("anna", "tacos000", "IYWMhbU1NCBF7o0Putz5C52EnzmFaz2Nz5BDCNAn", "OIOftryscsaETXUb6amf5DPA8ewpXYX0vpSDtzftljyBi0cbLAkOj8C5uIz5xIpOzLugHvZQNcs7AS4MTpBUJr2QYPC733yQ2e8LWzLbdELJiAZu77PXy9O6qcUxbdIc", "password");
//
//        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new GsonBuilder().create().toJson(request));
//
//        Call<OAuthToken> token = api.create(loginService.class).LoginUser("application/json", body);
//        token.enqueue(new Callback<OAuthToken>() {
//            @Override
//            public void onResponse(Call<OAuthToken> call, Response<OAuthToken> response) {
//                Log.i("Myclass", response.body().accessToken);
//            }
//
//            @Override
//            public void onFailure(Call<OAuthToken> call, Throwable t) {
//                Log.i(this.getClass().getSimpleName(), t.getMessage());
//            }
//        });
        //Log.i(this.getClass().getSimpleName(), token.r);

    }

    private void initialiseDependencies() {
//        DaggerApplicationComponent()
    }

    private void setBottomNavigationOnClickListener() {

        binding.communityGarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != CurrentTab.COMMUNITY_GARDEN) {
                    selectedTab = CurrentTab.COMMUNITY_GARDEN;
                    binding.nurseryButton.setTextColor(getResources().getColor(R.color.black));
                    binding.nurseryButton.setBackgroundColor(getResources().getColor(R.color.white));
                    binding.myGarden.setTextColor(getResources().getColor(R.color.black));
                    binding.myGarden.setBackgroundColor(getResources().getColor(R.color.white));
                    binding.communityGarden.setTextColor(getResources().getColor(R.color.white));
                    binding.communityGarden.setBackgroundColor(getResources().getColor(R.color.bottom_navigation_color));
                    navigateToCommunityGarden();
                }
            }
        });

        binding.nurseryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab = CurrentTab.NURSERY;
                binding.communityGarden.setTextColor(getResources().getColor(R.color.black));
                binding.communityGarden.setBackgroundColor(getResources().getColor(R.color.white));
                binding.myGarden.setTextColor(getResources().getColor(R.color.black));
                binding.myGarden.setBackgroundColor(getResources().getColor(R.color.white));
                binding.nurseryButton.setTextColor(getResources().getColor(R.color.white));
                binding.nurseryButton.setBackgroundColor(getResources().getColor(R.color.bottom_navigation_color));
                navigateToNursery();
            }
        });

        binding.myGarden.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectedTab = CurrentTab.MY_GARDEN;
                binding.communityGarden.setTextColor(getResources().getColor(R.color.black));
                binding.communityGarden.setBackgroundColor(getResources().getColor(R.color.white));
                binding.nurseryButton.setTextColor(getResources().getColor(R.color.black));
                binding.nurseryButton.setBackgroundColor(getResources().getColor(R.color.white));
                binding.myGarden.setTextColor(getResources().getColor(R.color.white));
                binding.myGarden.setBackgroundColor(getResources().getColor(R.color.bottom_navigation_color));
            }
        });


    }

    private void navigateToCommunityGarden() {
        // Create new fragment and transaction
        Fragment newFragment = CommunityGardenFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    private void navigateToNursery() {
        Fragment newFragment = NurseryFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }


//    private enum LoginResponse {
//        SUCCESS, WRONG_CREDENTIALS, NO_INTERNET, IO_ERROR
//    }
//
//    public class UserLogin extends AsyncTask<Void, Void, LoginResponse> {
//
//        WellnessUser user;
//
//
//        @Override
//        protected LoginResponse doInBackground(Void... voids) {
//            try {
//                user = new WellnessUser(DEFAULT_USERNAME, DEFAULT_PASSWORD, client_id, client_secret, SERVER_URL, OAUTH_TOKEN_PATH);
//                return LoginResponse.SUCCESS;
//            } catch (OAuth2Exception e) {
//                e.printStackTrace();
//                return LoginResponse.WRONG_CREDENTIALS;
//            } catch (IOException e) {
//                e.printStackTrace();
//                return LoginResponse.IO_ERROR;
//            }
//
//        }
//    }
}

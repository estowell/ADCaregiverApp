package edu.neu.ccs.wellness.adcaregiverapp.repository;

import javax.inject.Inject;

import edu.neu.ccs.wellness.adcaregiverapp.network.services.ChallengeServicesInterface;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.AvailableChallenges;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static edu.neu.ccs.wellness.adcaregiverapp.presentation.challenges.ChallengesViewModel.ChallengeViewModelCallback;

public class ChallengesRepository {


    private ChallengeServicesInterface availableChallengesInterface;

    @Inject
    public ChallengesRepository(Retrofit retrofit) {
        availableChallengesInterface = retrofit.create(ChallengeServicesInterface.class);

    }

    public void getAvailableChallenges(final ChallengeViewModelCallback callback) {
        Call<AvailableChallenges> call = availableChallengesInterface.getAvailableChallenges("Bearer lTP0KOIIZySMJkqqjrdB2mDTF9e1Wo");

        call.enqueue(new Callback<AvailableChallenges>() {
            @Override
            public void onResponse(Call<AvailableChallenges> call, Response<AvailableChallenges> response) {
                if (response.errorBody() == null) {
                    callback.success(response);
                } else {
                    callback.error(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<AvailableChallenges> call, Throwable t) {

            }
        });
    }

}

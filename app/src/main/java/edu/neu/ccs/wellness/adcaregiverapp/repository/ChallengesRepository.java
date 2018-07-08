package edu.neu.ccs.wellness.adcaregiverapp.repository;

import javax.inject.Inject;

import edu.neu.ccs.wellness.adcaregiverapp.common.utils.UserManager;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.AvailableChallenges;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.retrofitInterfaces.ChallengeServicesInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static edu.neu.ccs.wellness.adcaregiverapp.presentation.challenges.ChallengesViewModel.ChallengeViewModelCallback;

public class ChallengesRepository {

    private UserManager userManager;
    private ChallengeServicesInterface availableChallengesInterface;

    @Inject
    public ChallengesRepository(Retrofit retrofit, UserManager userManager) {
        availableChallengesInterface = retrofit.create(ChallengeServicesInterface.class);
        this.userManager = userManager;
    }

    public void getAvailableChallenges(final ChallengeViewModelCallback callback) {

        Call<AvailableChallenges> call = availableChallengesInterface.getAvailableChallenges();

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

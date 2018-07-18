package edu.neu.ccs.wellness.adcaregiverapp.presentation.communityGarden;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import edu.neu.ccs.wellness.adcaregiverapp.common.utils.UserManager;
import edu.neu.ccs.wellness.adcaregiverapp.domain.UseCase;
import edu.neu.ccs.wellness.adcaregiverapp.domain.circles.usecase.GetCircleUseCase;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.Member;
import edu.neu.ccs.wellness.adcaregiverapp.repository.UserRepository;

/**
 * Created by amritanshtripathi on 6/10/18.
 */

public class CommunityGardenViewModel extends ViewModel {


    private UserManager userManager;
    private UserRepository repository;
    private MutableLiveData<List<Member>> membersLiveData;
    private MutableLiveData<String> errorLiveData;

    @Inject
    public CommunityGardenViewModel(UserManager userManager, UserRepository repository) {
        this.userManager = userManager;
        this.repository = repository;
    }


    public MutableLiveData<List<Member>> getMembersLiveData() {
        if (membersLiveData == null) {
            membersLiveData = new MutableLiveData<>();
        }
        return membersLiveData;
    }


    public MutableLiveData<String> getErrorLiveData() {
        if (errorLiveData == null) {
            errorLiveData = new MutableLiveData<>();
        }
        return errorLiveData;
    }

    public void getMembers() {

        int userId = Objects.requireNonNull(userManager.getUser()).getUserId();

        GetCircleUseCase circleUseCase = new GetCircleUseCase(new UseCase.UseCaseCallback<GetCircleUseCase.Response>() {

            @Override
            public void onSuccess(GetCircleUseCase.Response response) {

                List<Member> members = response.getMembers();
                if (members.size() < 7) {
                    while (members.size() != 7) {
                        members.add(new Member());
                    }

                } else if (members.size() % 2 == 0) {
                    members.add(new Member());
                }

                membersLiveData.setValue(response.getMembers());

            }

            @Override
            public void onError(GetCircleUseCase.Response response) {
                errorLiveData.setValue("Error Fetching members");
            }

            @Override
            public void onFailure() {
                errorLiveData.setValue("Failed to Fetch members");
            }
        }, repository);
        circleUseCase.setRequestValues(new GetCircleUseCase.Request(userId));
        circleUseCase.run();

    }

}

package edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import edu.neu.ccs.wellness.adcaregiverapp.domain.Nursery.usecase.ShareStory;
import edu.neu.ccs.wellness.adcaregiverapp.domain.UseCase;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.Firebase;

public class NurseryViewModel extends ViewModel {

    private ShareStory shareStory;
    private MutableLiveData<Firebase.Status> sharePost;

    public MutableLiveData<Firebase.Status> getSharePost() {
        return sharePost;
    }

    public void sharePost(@NonNull String userName, @NonNull String message, @NonNull int userId) {
        shareStory = new ShareStory(new UseCase.UseCaseCallback<ShareStory.ResponseValue>() {
            @Override
            public void onSuccess(ShareStory.ResponseValue response) {
                sharePost.setValue(response.getStatus());
            }

            @Override
            public void onError(ShareStory.ResponseValue response) {
                sharePost.setValue(response.getStatus());
            }

            @Override
            public void onFailure() {

            }
        });
    }


}

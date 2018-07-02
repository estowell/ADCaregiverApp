package edu.neu.ccs.wellness.adcaregiverapp.repository;

import edu.neu.ccs.wellness.adcaregiverapp.domain.Nursery.model.StoryPost;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.Firebase;

public class NurseryRepository {


    private Firebase firebaseService = new Firebase();

    public void postStory(StoryPost post, FireBaseCallBack callBack) {
        firebaseService.postStory(post, callBack);
    }

    public interface FireBaseCallBack<T> {

        Firebase.Status callBackMethod(Firebase.Status status);
    }


}

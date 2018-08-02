package edu.neu.ccs.wellness.adcaregiverapp.network.services;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.neu.ccs.wellness.adcaregiverapp.domain.nursery.model.StoryPost;
import edu.neu.ccs.wellness.adcaregiverapp.repository.NurseryRepository;

public class Firebase {

    public enum Status {
        SUCCESS,
        ERROR
    }

    public void postStory(StoryPost post, final NurseryRepository.FireBaseCallBack callBack) {

        final DatabaseError[] response = new DatabaseError[1];
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("user-stories").child(String.valueOf(post.userId));
        myRef.setValue(post, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {
                    callBack.callBackMethod(Status.SUCCESS);
                } else {
                    callBack.callBackMethod(Status.ERROR);
                }

            }
        });
    }


    public void LogExercise(int UserId) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();


//        DatabaseReference reference = database.getReference().child("exercise-log").child()
    }


}

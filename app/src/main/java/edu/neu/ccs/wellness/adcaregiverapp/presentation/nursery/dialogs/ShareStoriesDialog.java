package edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.Objects;

import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.common.utils.NumberUtils;
import edu.neu.ccs.wellness.adcaregiverapp.databinding.DialogShareStoryBinding;
import edu.neu.ccs.wellness.adcaregiverapp.domain.nursery.model.StoryPost;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.CurrentChallenge;

import static edu.neu.ccs.wellness.adcaregiverapp.common.utils.Constants.CURRENT_CHALLENGE;
import static edu.neu.ccs.wellness.adcaregiverapp.common.utils.Constants.TOTAL_POST_COUNT;
import static edu.neu.ccs.wellness.adcaregiverapp.common.utils.Constants.USER_POST_COUNT;
import static edu.neu.ccs.wellness.adcaregiverapp.common.utils.Constants.USER_STORIES;

public class ShareStoriesDialog extends android.support.v4.app.DialogFragment {

    private static final double DIALOG_TO_SCREEN_HEIGHT_RATIO = .9;
    private static final String PROGRESS_VALUE = "progress_value";
    private static final String USER_NAME = "username";
    private static final String USER_ID = "USER_ID";

    private DialogShareStoryBinding binding;
    private Integer donutProgress = 0;
    private Dialog dialog;
    private String userName;
    private AlertDialog sharePostDialog;
    private int userId;

    public static ShareStoriesDialog newInstance(Integer progress, String userName, int userId) {

        Bundle args = new Bundle();
        args.putInt(PROGRESS_VALUE, progress);
        args.putString(USER_NAME, userName);
        args.putInt(USER_ID, userId);
        ShareStoriesDialog fragment = new ShareStoriesDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_share_story, container, false);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(PROGRESS_VALUE)) {
            userName = bundle.getString(USER_NAME);
            userId = bundle.getInt(USER_ID);
            donutProgress = bundle.getInt(PROGRESS_VALUE);
        }
        init();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        dialog = new Dialog(Objects.requireNonNull(this.getContext()));
        dialog.setContentView(R.layout.dialog_share_story);
        Window window = dialog.getWindow();
        Objects.requireNonNull(window).setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                (int) (NumberUtils.getHeightPx(this.getContext()) * DIALOG_TO_SCREEN_HEIGHT_RATIO));
        window.setGravity(Gravity.CENTER);

        return dialog;
    }

    private void init() {
        dialog.findViewById(R.id.share_story).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Dialog", "ButtonClicked");

                navigateToSharePostDialog();
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.backtextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });


        dialog.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        DonutProgress donut = dialog.findViewById(R.id.stories);
        donut.setProgress(donutProgress);
        donut.setText(String.valueOf(donutProgress));
    }

    private void navigateToSharePostDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.dialog_story_post);
        sharePostDialog = builder.create();

        sharePostDialog.show();
        sharePostDialog.findViewById(R.id.share_post_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ShareDialog", "ButtonClicked");
                updateOnFirebase();


            }
        });

    }

    private void updateOnFirebase() {

        EditText editText = sharePostDialog.findViewById(R.id.story_message_edittext);
        final String message = editText.getText().toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference();
        final DatabaseReference postCountref = databaseReference.child(TOTAL_POST_COUNT);
        final Integer[] count = {0};
        final DatabaseReference userStories = databaseReference.child(USER_STORIES);
        final DatabaseReference userPostCount = databaseReference.child(USER_POST_COUNT);

        postCountref.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                count[0] = mutableData.getValue(Integer.class);
                if (count[0] == null) {
                    count[0] = 1;
                    mutableData.setValue(1);
                } else {
                    mutableData.setValue(++count[0]);
                }

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                DatabaseReference childref = userStories.child(String.valueOf(count[0]));
                final Integer[] postcount = new Integer[1];
                childref.setValue(new StoryPost(message, userId, userName)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DatabaseReference reference = userPostCount.child(String.valueOf(userId));

                        reference.runTransaction(new Transaction.Handler() {
                            @NonNull
                            @Override
                            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                                postcount[0] = mutableData.getValue(Integer.class);
                                if (postcount[0] == null) {
                                    mutableData.setValue(1);
                                } else {
                                    postcount[0]++;
                                    mutableData.setValue(postcount[0]);
                                }
                                return Transaction.success(mutableData);
                            }

                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {


                                updateCurrentChallengeOnFirebase(databaseReference);
                                sharePostDialog.dismiss();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity().getBaseContext(), "Please Try again", Toast.LENGTH_LONG).show();
                    }
                });
            }

        });
    }

    private void updateCurrentChallengeOnFirebase(DatabaseReference databaseReference) {
        DatabaseReference currentChallengeReference = databaseReference.child(CURRENT_CHALLENGE).child(String.valueOf(userId));
        currentChallengeReference.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                CurrentChallenge currentChallenge = mutableData.getValue(CurrentChallenge.class);
                if (currentChallenge != null && currentChallenge.isRunning()) {
                    int numberOfPosts = currentChallenge.getNumberOfPosts();
                    currentChallenge.setNumberOfPosts(numberOfPosts + 1);
                    mutableData.setValue(currentChallenge);


                }

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

            }
        });
    }
}

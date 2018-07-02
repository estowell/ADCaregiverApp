package edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

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
import edu.neu.ccs.wellness.adcaregiverapp.domain.Nursery.model.StoryPost;

public class ShareStoriesDialog extends DialogFragment {

    private static final double DIALOG_TO_SCREEN_HEIGHT_RATIO = .8;
    private static final String PROGRESS_VALUE = "progress_value";
    private static final String USER_NAME = "username";

    private DialogShareStoryBinding binding;
    private float donutProgress = 0;
    private Dialog dialog;
    private String userName;
    private AlertDialog sharePostDialog;

    public static ShareStoriesDialog newInstance(float progress, String userName) {

        Bundle args = new Bundle();
        args.putFloat(PROGRESS_VALUE, progress);
        args.putString(USER_NAME, userName);
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
            donutProgress = bundle.getFloat(PROGRESS_VALUE);
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
        binding.stories.setProgress(donutProgress);
        binding.stories.setText(String.valueOf((int) donutProgress));
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
        DatabaseReference databaseReference = database.getReference();
        final DatabaseReference postCountref = databaseReference.child("postCount");
        final Integer[] count = {0};
        final DatabaseReference userStories = databaseReference.child("user-stories");

        postCountref.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                count[0] = mutableData.getValue(Integer.class);
                if (count[0] == null) {
                    mutableData.setValue(1);
                } else {
                    mutableData.setValue(++count[0]);
                }

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                DatabaseReference childref = userStories.child(String.valueOf(count[0]));
                childref.setValue(new StoryPost(message, count[0].intValue(), userName)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        sharePostDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Please Try again", Toast.LENGTH_LONG);
                    }
                });

            }
        });
    }
}

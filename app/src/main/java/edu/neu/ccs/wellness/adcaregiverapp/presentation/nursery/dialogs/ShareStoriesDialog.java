package edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery.dialogs;

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

import java.util.Objects;

import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.databinding.DialogShareStoryBinding;
import edu.neu.ccs.wellness.adcaregiverapp.utils.NumberUtils;

public class ShareStoriesDialog extends DialogFragment {

    private static final double DIALOG_TO_SCREEN_HEIGHT_RATIO = .8;
    private static final String PROGRESS_VALUE = "progress_value";

    private DialogShareStoryBinding binding;
    private float donutProgress = 0;
    private Dialog dialog;

    public static ShareStoriesDialog newInstance(float progress) {

        Bundle args = new Bundle();
        args.putFloat(PROGRESS_VALUE, progress);
        ShareStoriesDialog fragment = new ShareStoriesDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_share_story, container, false);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(PROGRESS_VALUE)) {

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
}

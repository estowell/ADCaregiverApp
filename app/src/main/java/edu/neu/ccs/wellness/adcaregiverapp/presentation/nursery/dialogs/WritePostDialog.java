package edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery.NurseryViewModel;

public class WritePostDialog extends android.support.v4.app.DialogFragment {

    private AlertDialog dialog;


    public static WritePostDialog newInstance() {

        Bundle args = new Bundle();

        WritePostDialog fragment = new WritePostDialog();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert this.getParentFragment() != null;
        assert this.getParentFragment().getParentFragment() != null;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.dialog_story_post);
        dialog = builder.create();

        dialog.show();
        dialog.findViewById(R.id.share_post_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ShareDialog", "ButtonClicked");


            }
        });
        return dialog;
    }
}

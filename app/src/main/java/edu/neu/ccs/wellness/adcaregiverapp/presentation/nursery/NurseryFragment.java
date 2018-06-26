package edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.databinding.FragmentNurseryBinding;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery.dialogs.ShareStoriesDialog;

/**
 * Created by amritanshtripathi on 6/12/18.
 */

public class NurseryFragment extends Fragment {

    private FragmentNurseryBinding binding;

    public static NurseryFragment newInstance() {

        Bundle args = new Bundle();

        NurseryFragment fragment = new NurseryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nursery, container, false);
        init();
        return binding.getRoot();
    }


    private void init() {
        binding.exerciseProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStoriesDialog();
            }
        });
    }

    private void showStoriesDialog() {
        ShareStoriesDialog dialog = ShareStoriesDialog.newInstance(binding.exerciseProgress.getProgress());
        assert getFragmentManager() != null;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        dialog.show(ft, ShareStoriesDialog.class.getSimpleName());

    }
}

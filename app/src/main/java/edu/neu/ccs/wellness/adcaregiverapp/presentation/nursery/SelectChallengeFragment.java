package edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.databinding.FragmentSelectChallengeBinding;

public class SelectChallengeFragment extends Fragment {

    private FragmentSelectChallengeBinding binding;

    public static SelectChallengeFragment newInstance() {

        Bundle args = new Bundle();

        SelectChallengeFragment fragment = new SelectChallengeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_challenge, container, false);

        return binding.getRoot();
    }

}

package edu.neu.ccs.wellness.adcaregiverapp.presentation.challenges.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import javax.inject.Inject;

import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.UnitChallenge;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.challenges.ChallengesViewModel;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.challenges.ChallengesViewModelFactory;

public class AcceptChallengeFragment extends Fragment {


    private static String UNIT_CHALLENGE = "UNIT_CHALLENGE";
    private ChallengesViewModel viewModel;
    private UnitChallenge unitChallenge;

    @Inject
    ChallengesViewModelFactory viewModelFactory;

    public static AcceptChallengeFragment newInstance(UnitChallenge unitChallenge) {

        Bundle args = new Bundle();
        args.putParcelable(UNIT_CHALLENGE, unitChallenge);
        AcceptChallengeFragment fragment = new AcceptChallengeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), viewModelFactory).get(ChallengesViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_accept_challenge, container, false);
        Bundle bundle = getArguments();

        if (bundle != null) {
            unitChallenge = bundle.getParcelable(UNIT_CHALLENGE);
        }
        init(view);
        return view;
    }

    private void init(View view) {

    }
}

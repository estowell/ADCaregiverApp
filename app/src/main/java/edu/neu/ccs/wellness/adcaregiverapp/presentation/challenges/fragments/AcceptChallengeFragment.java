package edu.neu.ccs.wellness.adcaregiverapp.presentation.challenges.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Objects;

import javax.inject.Inject;

import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.databinding.FragmentAcceptChallengeBinding;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.UnitChallenge;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.ViewModelFactory;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.challenges.ChallengesViewModel;

public class AcceptChallengeFragment extends Fragment {


    private static String UNIT_CHALLENGE = "UNIT_CHALLENGE";
    private ChallengesViewModel viewModel;
    private UnitChallenge unitChallenge;

    @Inject
    ViewModelFactory viewModelFactory;

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


        FragmentAcceptChallengeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_accept_challenge, container, false);

        Bundle bundle = getArguments();

        if (bundle != null) {
            unitChallenge = bundle.getParcelable(UNIT_CHALLENGE);
        }
        init(binding);
        return binding.getRoot();
    }

    private void init(FragmentAcceptChallengeBinding binding) {

        Observer<Boolean> acceptChallenge = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    Objects.requireNonNull(getActivity()).finish();
                } else {
                    Toast.makeText(getContext(), "Error while making the request, please try again!", Toast.LENGTH_LONG).show();
                }
            }
        };

        binding.header.setText(unitChallenge.total_duration + " Days Challenge");
        binding.subheader.setText(unitChallenge.text);
        binding.acceptChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.acceptChallenge(unitChallenge);
            }
        });

        binding.changeChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getFragmentManager()).popBackStack();
            }
        });

        viewModel.getAcceptChallengeResponse().observe(this, acceptChallenge);
    }
}

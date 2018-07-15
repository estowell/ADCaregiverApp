package edu.neu.ccs.wellness.adcaregiverapp.presentation.challenges.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import javax.inject.Inject;

import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.databinding.FragmentSelectChallengeBinding;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.AvailableChallenges;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.UnitChallenge;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.ViewModelFactory;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.challenges.ChallengesViewModel;

public class SelectChallengeFragment extends Fragment {

    private FragmentSelectChallengeBinding binding;
    private ChallengesViewModel viewModel;

    @Inject
    ViewModelFactory viewModelFactory;

    public static SelectChallengeFragment newInstance() {

        Bundle args = new Bundle();

        SelectChallengeFragment fragment = new SelectChallengeFragment();
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_challenge, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {

        binding.back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).finish();
            }
        });

        binding.option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAcceptChallengeFragment(viewModel.getAvailableChallenges().challenges.get(0));

            }
        });

        binding.option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAcceptChallengeFragment(viewModel.getAvailableChallenges().challenges.get(1));
            }
        });

        binding.option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAcceptChallengeFragment(viewModel.getAvailableChallenges().challenges.get(2));
            }
        });

        binding.container.setVisibility(View.GONE);
        binding.progressBarSelectChallenges.setVisibility(View.VISIBLE);

        Observer<AvailableChallenges> availableChallengesObserver = new Observer<AvailableChallenges>() {
            @Override
            public void onChanged(@Nullable AvailableChallenges challenges) {
                binding.progressBarSelectChallenges.setVisibility(View.GONE);
                binding.container.setVisibility(View.VISIBLE);
            }
        };

        Observer<String> errorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.progressBarSelectChallenges.setVisibility(View.GONE);
                binding.container.setVisibility(View.VISIBLE);

            }
        };

        viewModel.getAvailableChallengesLiveData().observe(this, availableChallengesObserver);
        viewModel.getErrorLiveData().observe(this, errorObserver);
        viewModel.fetchAvailableChallenges();
    }

    private void navigateToAcceptChallengeFragment(UnitChallenge unitChallenge) {

        FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        AcceptChallengeFragment acceptChallengeFragment = AcceptChallengeFragment.newInstance(unitChallenge);
        ft.replace(R.id.challenges_container, acceptChallengeFragment);
        ft.addToBackStack(null);
        ft.commit();
    }


}

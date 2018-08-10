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
import android.widget.Toast;

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
    private ChallengeSelected challengeSelected;
    private UnitChallenge currUnitChallenge;

    public enum ChallengeSelected {
        LESS_THAN,
        MORE_THAN,
        SAME_AS
    }

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


        Observer<AvailableChallenges> availableChallengesObserver = new Observer<AvailableChallenges>() {
            @Override
            public void onChanged(@Nullable AvailableChallenges challenges) {
                setOnClickListners(challenges);
            }
        };

        Observer<String> errorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
            }
        };

        viewModel.getAvailableChallengesLiveData().observe(this, availableChallengesObserver);
        viewModel.getErrorLiveData().observe(this, errorObserver);
        viewModel.fetchAvailableChallenges();

    }

    private void setOnClickListners(final AvailableChallenges challenges) {
        binding.lessThan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currUnitChallenge = challenges.getChallenges().get(0);
                challengeSelected = ChallengeSelected.LESS_THAN;
                updateView();
            }
        });
        binding.MoreThan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                currUnitChallenge = challenges.getChallenges().get(1);
                challengeSelected = ChallengeSelected.MORE_THAN;
                updateView();
            }
        });

        binding.same.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currUnitChallenge = challenges.getChallenges().get(2);
                challengeSelected = ChallengeSelected.SAME_AS;
                updateView();
            }
        });
    }

    private void updateView() {
        switch (challengeSelected) {
            case LESS_THAN:
                binding.subheader.setText("This week, my step goal is LESS THAN last week");
                binding.lessThanImagesContainer.setVisibility(View.VISIBLE);
                binding.MoreThanImagesContainer.setVisibility(View.GONE);
                binding.sameImagesContainer.setVisibility(View.GONE);
                break;
            case SAME_AS:
                binding.subheader.setText("This week, my step goal is SAME AS last week");
                binding.lessThanImagesContainer.setVisibility(View.GONE);
                binding.MoreThanImagesContainer.setVisibility(View.GONE);
                binding.sameImagesContainer.setVisibility(View.VISIBLE);
                break;
            case MORE_THAN:
                binding.subheader.setText("This week, my step goal is MORE THAN last week");
                binding.lessThanImagesContainer.setVisibility(View.GONE);
                binding.MoreThanImagesContainer.setVisibility(View.VISIBLE);
                binding.sameImagesContainer.setVisibility(View.GONE);
                break;
        }
    }


    private void navigateToAcceptChallengeFragment(String imageName) {

        FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        AcceptChallengeFragment acceptChallengeFragment = AcceptChallengeFragment.newInstance(currUnitChallenge, imageName);
        ft.replace(R.id.challenges_container, acceptChallengeFragment);
        ft.addToBackStack(null);
        ft.commit();
    }


    //    private void init() {
//
//        binding.back3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Objects.requireNonNull(getActivity()).finish();
//            }
//        });
//
//        binding.option1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navigateToAcceptChallengeFragment(viewModel.getAvailableChallenges().challenges.get(0));
//
//            }
//        });
//
//        binding.option2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navigateToAcceptChallengeFragment(viewModel.getAvailableChallenges().challenges.get(1));
//            }
//        });
//
//        binding.option3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navigateToAcceptChallengeFragment(viewModel.getAvailableChallenges().challenges.get(2));
//            }
//        });
//
//        binding.container.setVisibility(View.GONE);
//        binding.progressBarSelectChallenges.setVisibility(View.VISIBLE);
//
//        Observer<AvailableChallenges> availableChallengesObserver = new Observer<AvailableChallenges>() {
//            @Override
//            public void onChanged(@Nullable AvailableChallenges challenges) {
//                binding.progressBarSelectChallenges.setVisibility(View.GONE);
//                binding.container.setVisibility(View.VISIBLE);
//                updateButtons(challenges.challenges);
//
//            }
//        };
//
//        Observer<String> errorObserver = new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                binding.progressBarSelectChallenges.setVisibility(View.GONE);
//                binding.container.setVisibility(View.VISIBLE);
//
//            }
//        };
//
//        viewModel.getAvailableChallengesLiveData().observe(this, availableChallengesObserver);
//        viewModel.getErrorLiveData().observe(this, errorObserver);
//        viewModel.fetchAvailableChallenges();
//    }
//
//    private void updateButtons(List<UnitChallenge> challenges) {
//        binding.option1.setText(challenges.get(0).text);
//        binding.option2.setText(challenges.get(1).text);
//        binding.option3.setText(challenges.get(2).text);
//    }
//


}

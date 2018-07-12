package edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.common.utils.UserManager;
import edu.neu.ccs.wellness.adcaregiverapp.databinding.FragmentNurseryBinding;
import edu.neu.ccs.wellness.adcaregiverapp.domain.login.model.User;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.MainActivity;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.ViewModelFactory;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery.dialogs.ShareStoriesDialog;

/**
 * Created by amritanshtripathi on 6/12/18.
 */

public class NurseryFragment extends DaggerFragment {

    private FragmentNurseryBinding binding;

    private NurseryViewModel viewModel;
    private User user;

    @Inject
    ViewModelFactory viewModelFactory;

    @Inject
    UserManager userManager;

    public static NurseryFragment newInstance() {

        Bundle args = new Bundle();

        NurseryFragment fragment = new NurseryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NurseryViewModel.class);
        getUser();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nursery, container, false);
        init();
        return binding.getRoot();
    }


    private void init() {
        binding.nurseryProgressBar.setVisibility(View.VISIBLE);

        Observer<Boolean> isChallengeRunning = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                binding.nurseryProgressBar.setVisibility(View.GONE);
                if (aBoolean) {
                    viewModel.getSevenDaysActivity();
                    binding.selectNewChallenge.setVisibility(View.GONE);
                    binding.flowerImage.setVisibility(View.VISIBLE);
                } else {
                    binding.selectNewChallenge.setVisibility(View.VISIBLE);
                    binding.flowerImage.setVisibility(View.GONE);
                }
            }
        };

        Observer<Integer> stepsPercentage = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer sPercentage) {
                binding.stepsBar.setProgress(sPercentage);
            }
        };
        binding.exerciseProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStoriesDialog();
            }
        });
        binding.selectNewChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToChallengeActivity();
            }
        });
        viewModel.getStepsLiveData().observe(this, stepsPercentage);
        viewModel.getRunningChallengeLiveData().observe(this, isChallengeRunning);
        viewModel.isChallengeRunning();

    }

    private void navigateToChallengeActivity() {
        MainActivity mainActivity = (MainActivity) getActivity();
        assert mainActivity != null;
        mainActivity.startChallengeActivityForResult();
    }

    private void showStoriesDialog() {
        ShareStoriesDialog dialog = ShareStoriesDialog.newInstance(binding.exerciseProgress.getProgress(), user.getUsername());
        assert getFragmentManager() != null;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        dialog.show(ft, ShareStoriesDialog.class.getSimpleName());

    }

    private void getUser() {
        user = userManager.getUser();
    }
}

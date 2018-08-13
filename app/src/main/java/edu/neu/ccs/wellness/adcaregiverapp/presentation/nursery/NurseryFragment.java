package edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.common.utils.UserManager;
import edu.neu.ccs.wellness.adcaregiverapp.databinding.FragmentNurseryBinding;
import edu.neu.ccs.wellness.adcaregiverapp.domain.activities.model.Activities;
import edu.neu.ccs.wellness.adcaregiverapp.domain.login.model.User;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.MainActivity;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.ViewModelFactory;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises.ExerciseFragment;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.gardenGazette.GardenGazetteFragment;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery.weeklyProgress.WeeklyProgressFragment;

import static edu.neu.ccs.wellness.adcaregiverapp.common.utils.Constants.USER_POST_COUNT;

/**
 * Created by amritanshtripathi on 6/12/18.
 */

public class NurseryFragment extends DaggerFragment {

    private FragmentNurseryBinding binding;

    private NurseryViewModel viewModel;
    private User user;
    private Integer storiesProgress = 0;
    private MainActivity activity;
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
        activity = (MainActivity) getActivity();
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
        updateStoriesProgress();
        if (activity != null) {
            activity.showBottomNavigation();
            activity.setSelectedTab(MainActivity.CurrentTab.NURSERY);
        }
        binding.nurseryProgressBar.setVisibility(View.VISIBLE);

        binding.stepsBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.stepsBar.getProgress() > 0) {
                    navigateToWeeklyProgress();
                }

            }
        });
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

        binding.selectNewChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToChallengeActivity();
            }
        });

        binding.stories.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showExercisesFragment();
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

    private void navigateToWeeklyProgress() {
        ArrayList<Activities> activities = (ArrayList<Activities>) viewModel.getActivities();
        Fragment fragment = WeeklyProgressFragment.newInstance(activities);
        FragmentTransaction ft = Objects.requireNonNull(getFragmentManager()).beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();

    }

    private void showStoriesDialog() {
        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = Objects.requireNonNull(fragmentManager).beginTransaction();

        transaction.replace(R.id.fragment_container, GardenGazetteFragment.newInstance(storiesProgress, user.getUsername(), user.getUserId()));
        transaction.addToBackStack(null);
        transaction.commit();

    }


    private void showExercisesFragment() {
        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = Objects.requireNonNull(fragmentManager).beginTransaction();
        ExerciseFragment fragment = ExerciseFragment.newInstance();

        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void getUser() {
        user = userManager.getUser();
    }


    private void updateStoriesProgress() {
        int userId = Objects.requireNonNull(userManager.getUser()).getUserId();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference().child(USER_POST_COUNT).child(String.valueOf(userId));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer count = dataSnapshot.getValue(Integer.class);
                if (count == null) {

                    storiesProgress = 0;
                    binding.exerciseProgress.setProgress(0);
                } else if (count % 2 == 0) {
                    storiesProgress = 100;
                    binding.exerciseProgress.setProgress(100);
                } else {
                    storiesProgress = 50;
                    binding.exerciseProgress.setProgress(50);
                }

                binding.exerciseProgress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showStoriesDialog();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}

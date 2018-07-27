package edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises.viewPagerFragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dagger.android.support.DaggerFragment;
import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.databinding.FragmentExerciseTutorialBinding;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.MainActivity;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises.ExerciseType;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises.tutorialFragments.TutorialListFragment;

public class ExerciseTutorialFragment extends DaggerFragment {
    private FragmentExerciseTutorialBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_exercise_tutorial, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        binding.balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToTutorialList(ExerciseType.BALANCE);
            }
        });
        binding.endurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToTutorialList(ExerciseType.ENDURANCE);
            }
        });

        binding.flexibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToTutorialList(ExerciseType.FLEXIBILITY);
            }
        });

        binding.strength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToTutorialList(ExerciseType.STRENGTH);
            }
        });
    }


    private void navigateToTutorialList(ExerciseType type) {
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
                activity.getAdapter().switchFragments(TutorialListFragment.newInstance(type));

        }
    }



}

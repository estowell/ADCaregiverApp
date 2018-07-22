package edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises.viewPagerFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dagger.android.support.DaggerFragment;
import edu.neu.ccs.wellness.adcaregiverapp.R;

public class ExerciseTutorialFragment extends DaggerFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exercise_tutorial, container, false);
    }
}

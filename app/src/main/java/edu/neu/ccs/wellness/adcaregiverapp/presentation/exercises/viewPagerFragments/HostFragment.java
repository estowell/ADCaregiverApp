package edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises.viewPagerFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.neu.ccs.wellness.adcaregiverapp.R;

public class HostFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_host, container, false);
        ExerciseTutorialFragment fragment = new ExerciseTutorialFragment();
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.exercise_container, fragment).commit();
        return view;
    }

}

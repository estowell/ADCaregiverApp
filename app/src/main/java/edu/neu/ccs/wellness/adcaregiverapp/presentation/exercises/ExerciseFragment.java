package edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dagger.android.support.DaggerFragment;
import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.MainActivity;

public class ExerciseFragment extends DaggerFragment {

    private ExercisePagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MainActivity activity;


    public static ExerciseFragment newInstance() {

        Bundle args = new Bundle();

        ExerciseFragment fragment = new ExerciseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pagerAdapter = new ExercisePagerAdapter(getFragmentManager(), this.getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_exercise_main, container, false);
        viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);
        tabLayout = view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        init(view);
        return view;
    }

    private void init(View view) {
        activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.hideBottomNavigation();
            activity.setAdapter(pagerAdapter);
        }
        view.findViewById(R.id.back_exercise).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
                activity.showBottomNavigation();
            }
        });


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activity.setAdapter(null);
    }
}

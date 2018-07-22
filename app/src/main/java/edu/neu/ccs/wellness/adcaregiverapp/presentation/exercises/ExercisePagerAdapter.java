package edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises.viewPagerFragments.ExerciseLogFragment;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises.viewPagerFragments.ExerciseTutorialFragment;

public class ExercisePagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public ExercisePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ExerciseLogFragment();
        } else {
            return new ExerciseTutorialFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.logs);


            case 1:
                return context.getString(R.string.tutorial);


            default:
                return null;
        }
    }
}

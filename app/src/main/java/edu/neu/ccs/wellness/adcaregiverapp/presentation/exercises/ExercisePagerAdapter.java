package edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises.viewPagerFragments.ExerciseLogFragment;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises.viewPagerFragments.HostFragment;

public class ExercisePagerAdapter extends FragmentStatePagerAdapter {

    private Context context;

    private Fragment exerciseLogFragment;
    private Fragment exerciseTutorialFragment;
    private FragmentManager fragmentManager;

    public ExercisePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.fragmentManager = fm;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            if (exerciseLogFragment == null) {
                exerciseLogFragment = new ExerciseLogFragment();
            }
            return exerciseLogFragment;
        } else {
            if (exerciseTutorialFragment == null) {
                exerciseTutorialFragment = new HostFragment();
            }
            return exerciseTutorialFragment;
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
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

    public void switchFragments(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.exercise_container, fragment).commit();
        exerciseTutorialFragment = fragment;
//        notifyDataSetChanged();
    }

    public boolean onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            return true;
        } else {
            return false;
        }
    }

}

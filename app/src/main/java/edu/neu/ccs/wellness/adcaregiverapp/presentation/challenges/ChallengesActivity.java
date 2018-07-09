package edu.neu.ccs.wellness.adcaregiverapp.presentation.challenges;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.ViewModelFactory;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.challenges.fragments.SelectChallengeFragment;

public class ChallengesActivity extends DaggerAppCompatActivity {

    private ChallengesViewModel viewModel;

    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);
        init();
    }

    private void init() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChallengesViewModel.class);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        SelectChallengeFragment fragment = SelectChallengeFragment.newInstance();
        ft.replace(R.id.challenges_container, fragment);
        ft.commit();
    }

}

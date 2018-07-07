package edu.neu.ccs.wellness.adcaregiverapp.DI;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.MainActivity;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.challenges.ChallengesActivity;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.login.LoginActivity;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = FragmentBuilder.class)
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract ChallengesActivity challengesActivity();

    @ContributesAndroidInjector
    abstract LoginActivity loginActivity();
}

package edu.neu.ccs.wellness.adcaregiverapp.DI;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.challenges.fragments.SelectChallengeFragment;

@Module
public abstract class ChallengeActivityFragmentBuilder {

    @ContributesAndroidInjector
    abstract SelectChallengeFragment provideSelectChallengeFragment();
}

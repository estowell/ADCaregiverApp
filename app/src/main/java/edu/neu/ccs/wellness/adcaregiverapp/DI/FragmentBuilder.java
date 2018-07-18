package edu.neu.ccs.wellness.adcaregiverapp.DI;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.communityGarden.CommunityGardenFragment;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery.NurseryFragment;

@Module
public abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract NurseryFragment provideNurseryFragment();

    @ContributesAndroidInjector
    abstract CommunityGardenFragment provideCommunityGardenFragment();
}

package edu.neu.ccs.wellness.adcaregiverapp.DI;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.communityGarden.CommunityGardenFragment;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises.ExerciseFragment;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises.viewPagerFragments.ExerciseLogFragment;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises.viewPagerFragments.ExerciseTutorialFragment;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.garden.GardenFragment;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery.NurseryFragment;

@Module
public abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract NurseryFragment provideNurseryFragment();

    @ContributesAndroidInjector
    abstract CommunityGardenFragment provideCommunityGardenFragment();

    @ContributesAndroidInjector
    abstract GardenFragment provideGardenFragment();

    @ContributesAndroidInjector
    abstract ExerciseFragment provideExerciseFragment();

    @ContributesAndroidInjector
    abstract ExerciseTutorialFragment provideExerciseTutorialFragment();

    @ContributesAndroidInjector
    abstract ExerciseLogFragment provideExerciseLogFragment();

}

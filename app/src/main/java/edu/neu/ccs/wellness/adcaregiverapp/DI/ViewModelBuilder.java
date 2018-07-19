package edu.neu.ccs.wellness.adcaregiverapp.DI;

import android.arch.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.challenges.ChallengesViewModel;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.communityGarden.CommunityGardenViewModel;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.garden.GardenViewModel;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.login.LoginViewModel;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery.NurseryViewModel;

@Module
public abstract class ViewModelBuilder {


    @Binds
    @IntoMap
    @ViewModelKey(ChallengesViewModel.class)
    abstract ViewModel bindChallengeViewModel(ChallengesViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(NurseryViewModel.class)
    abstract ViewModel bindNurseryViewModel(NurseryViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CommunityGardenViewModel.class)
    abstract ViewModel bindGardenGazetteViewModel(CommunityGardenViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(GardenViewModel.class)
    abstract ViewModel bindGardenViewModel(GardenViewModel viewModel);
}

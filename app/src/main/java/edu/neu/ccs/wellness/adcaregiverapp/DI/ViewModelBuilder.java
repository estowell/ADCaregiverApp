package edu.neu.ccs.wellness.adcaregiverapp.DI;

import android.arch.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.challenges.ChallengesViewModel;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.login.LoginViewModel;

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
}

package edu.neu.ccs.wellness.adcaregiverapp.DI.component;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import edu.neu.ccs.wellness.adcaregiverapp.DI.module.MainActivityModule;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.MainActivity;

@Subcomponent(modules = MainActivityModule.class)
public interface MainActivityComponent extends AndroidInjector<MainActivity> {


    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity> {
    }
}

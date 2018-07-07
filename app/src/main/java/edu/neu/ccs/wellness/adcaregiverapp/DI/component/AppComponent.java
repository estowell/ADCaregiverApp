package edu.neu.ccs.wellness.adcaregiverapp.DI.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import edu.neu.ccs.wellness.adcaregiverapp.App;
import edu.neu.ccs.wellness.adcaregiverapp.DI.ActivityBuilder;
import edu.neu.ccs.wellness.adcaregiverapp.DI.ViewModelBuilder;
import edu.neu.ccs.wellness.adcaregiverapp.DI.module.AppModule;

/**
 * Created by amritanshtripathi on 6/20/18.
 */

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class,
        ViewModelBuilder.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(App app);
}
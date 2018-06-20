package edu.neu.ccs.wellness.adcaregiverapp.DI.component;

import android.app.Application;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.AndroidSupportInjectionModule;
import edu.neu.ccs.wellness.adcaregiverapp.App;
import edu.neu.ccs.wellness.adcaregiverapp.DI.ActivityBuilder;
import edu.neu.ccs.wellness.adcaregiverapp.DI.module.NetworkModules.AppModule;

/**
 * Created by amritanshtripathi on 6/20/18.
 */

@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(App app);
}
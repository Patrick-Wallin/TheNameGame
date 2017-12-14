package com.pwmobiledeveloper.projects.thenamegame.dagger.component;

import com.pwmobiledeveloper.projects.thenamegame.dagger.module.ApplicationModule;
import com.pwmobiledeveloper.projects.thenamegame.dagger.module.NetworksModule;
import com.pwmobiledeveloper.projects.thenamegame.fragment.NameGameFragment;
import com.pwmobiledeveloper.projects.thenamegame.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by piwal on 12/6/2017.
 */
@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworksModule.class
})
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
    void inject(NameGameFragment nameGameFragment);
}

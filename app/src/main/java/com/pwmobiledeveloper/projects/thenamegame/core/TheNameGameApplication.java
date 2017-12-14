package com.pwmobiledeveloper.projects.thenamegame.core;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.pwmobiledeveloper.projects.thenamegame.R;
import com.pwmobiledeveloper.projects.thenamegame.dagger.component.ApplicationComponent;
import com.pwmobiledeveloper.projects.thenamegame.dagger.component.DaggerApplicationComponent;
import com.pwmobiledeveloper.projects.thenamegame.dagger.module.ApplicationModule;
import com.pwmobiledeveloper.projects.thenamegame.dagger.module.NetworksModule;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Created by piwal on 12/6/2017.
 */

public class TheNameGameApplication extends Application {
    private ApplicationComponent applicationComponent;

    public static TheNameGameApplication get(@NonNull Context context) {
        return (TheNameGameApplication) context.getApplicationContext();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .networksModule(new NetworksModule(getString(R.string.main_url)))
                .build();

    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}


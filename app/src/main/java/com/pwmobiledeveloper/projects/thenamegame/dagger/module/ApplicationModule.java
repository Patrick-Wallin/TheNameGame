package com.pwmobiledeveloper.projects.thenamegame.dagger.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.util.Random;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by piwal on 12/6/2017.
 */

@Module
public class ApplicationModule {
    @NonNull
    private Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return mApplication;
    }

    @Provides @NonNull @Singleton
    public Context provideContext(@NonNull Application application) {
        return application;
    }

    /*
    @Provides @NonNull @Singleton
    public Gson provideGson() {
        return new Gson();
    }

    @Provides @NonNull @Singleton
    public Random provideRandom() {
        return new Random(System.currentTimeMillis());
    }


    @Provides @NonNull @Singleton
    public ListRandomizer provideListRandomizer(@NonNull Random random) {
        return new ListRandomizer(random);
    }
    */
}

package com.pwmobiledeveloper.projects.thenamegame.dagger.module;

import android.app.Application;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pwmobiledeveloper.projects.thenamegame.BuildConfig;
import com.pwmobiledeveloper.projects.thenamegame.rest.TheNameGameApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by piwal on 12/6/2017.
 */
@Module
public class NetworksModule {
    private String mUrlPath;

    public NetworksModule(String urlPath) {
        mUrlPath = urlPath;
    }

    @Provides
    @Singleton
    public Cache provideHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        GsonBuilder builder = new GsonBuilder();
        return builder.create();
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkhttpClient(Cache cache) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.cache(cache);
        return client.build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(mUrlPath)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides @NonNull
    @Singleton
    public TheNameGameApi provideApi(@NonNull Gson gson, @NonNull OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(mUrlPath)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .validateEagerly(BuildConfig.DEBUG)
                .build()
                .create(TheNameGameApi.class);
    }


}

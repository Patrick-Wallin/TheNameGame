package com.pwmobiledeveloper.projects.thenamegame.rest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pwmobiledeveloper.projects.thenamegame.model.Person;
import com.pwmobiledeveloper.projects.thenamegame.model.Profiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by piwal on 12/12/2017.
 */

public class ProfilesRespository {
    @NonNull
    private final TheNameGameApi api;
    @NonNull
    private List<Listener> listeners = new ArrayList<>(1);
    @Nullable
    private List<Person> profiles;

    @Inject
    public ProfilesRespository(@NonNull TheNameGameApi api, Listener... listeners) {
        this.api = api;
        if (listeners != null) {
            this.listeners = new ArrayList<>(Arrays.asList(listeners));
        }
        load();
    }

    private void load() {
        this.api.getProfiles().enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                profiles = response.body();
                for (Listener listener : listeners) {
                    listener.onLoadFinished(profiles);
                }
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                for (Listener listener : listeners) {
                    listener.onError(t);
                }
            }
        });
    }

    public void register(@NonNull Listener listener) {
        if (listeners.contains(listener)) throw new IllegalStateException("Listener is already registered.");
        listeners.add(listener);
        if (profiles != null) {
            listener.onLoadFinished(profiles);
        }
    }

    public void unregister(@NonNull Listener listener) {
        listeners.remove(listener);
    }

    public interface Listener {
        void onLoadFinished(@NonNull List<Person> people);
        void onError(@NonNull Throwable error);
    }

}

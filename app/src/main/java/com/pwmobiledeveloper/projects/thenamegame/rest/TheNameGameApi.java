package com.pwmobiledeveloper.projects.thenamegame.rest;

import com.pwmobiledeveloper.projects.thenamegame.model.Person;
import com.pwmobiledeveloper.projects.thenamegame.model.Profiles;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by piwal on 12/9/2017.
 */

public interface TheNameGameApi {
    @GET("/api/v1.0/profiles")
    Call<List<Person>> getProfiles();
}

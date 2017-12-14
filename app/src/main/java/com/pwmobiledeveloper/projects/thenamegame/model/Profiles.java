package com.pwmobiledeveloper.projects.thenamegame.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by piwal on 12/9/2017.
 */

public class Profiles {
    private List<Person> mPerson;

    public List<Person> getPerson() {
        return mPerson;
    }

    public void setPerson(List<Person> person) {
        mPerson = person;
    }
}

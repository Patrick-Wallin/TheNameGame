package com.pwmobiledeveloper.projects.thenamegame.listener;

import android.widget.ImageView;

import com.pwmobiledeveloper.projects.thenamegame.model.Person;

/**
 * Created by piwal on 12/9/2017.
 */

public interface OnNameGameFragmentInteractionListener {
    void onNameGameFragmentInteraction(Person person, String fullName, ImageView sharedImageView);
}

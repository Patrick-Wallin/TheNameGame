package com.pwmobiledeveloper.projects.thenamegame.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.pwmobiledeveloper.projects.thenamegame.R;
import com.pwmobiledeveloper.projects.thenamegame.core.TheNameGameApplication;
import com.pwmobiledeveloper.projects.thenamegame.fragment.NameGameFragment;
import com.pwmobiledeveloper.projects.thenamegame.listener.OnNameGameFragmentInteractionListener;
import com.pwmobiledeveloper.projects.thenamegame.model.Person;
import com.pwmobiledeveloper.projects.thenamegame.utilities.NetworkUtils;

import org.parceler.Parcels;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements OnNameGameFragmentInteractionListener {
    @Inject
    Retrofit mRetrofit;

    private static final String TAG_RETAINED_FRAGMENT = "RetainedFragment";
    private NameGameFragment mNameGameFragment;
    private boolean mComeFromDetailActivity = false;
    private boolean mRegularMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TheNameGameApplication.get(this).getApplicationComponent().inject(this);

        FragmentManager fm = getFragmentManager();
        mNameGameFragment = (NameGameFragment) fm.findFragmentByTag(TAG_RETAINED_FRAGMENT);

        loadNameGameFragment();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(mNameGameFragment != null) {
            if (!mComeFromDetailActivity) {
                mNameGameFragment.resumeTimer();
            } else {
                mNameGameFragment.shuffleAndReload();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void loadNameGameFragment() {
        if(mNameGameFragment == null) {
            mNameGameFragment = new NameGameFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.name_game_frame_layout, mNameGameFragment, TAG_RETAINED_FRAGMENT);
            fragmentTransaction.commit();
        }
    }


    @Override
    public void onNameGameFragmentInteraction(Person person, String fullName, ImageView sharedImageView) {
        if(fullName.equalsIgnoreCase(person.getFirstName() + " " + person.getLastName())) {
            if (mNameGameFragment != null) {
                mNameGameFragment.stopTimer();
                mNameGameFragment.updateAttempts(true);
            }
            mComeFromDetailActivity = true;
            Intent intentFaceDetailActivity = new Intent(this, FaceDetailActivity.class);
            intentFaceDetailActivity.putExtra(getString(R.string.person_info), Parcels.wrap(person));
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(this, (View)sharedImageView, ViewCompat.getTransitionName(sharedImageView));
            this.startActivity(intentFaceDetailActivity,options.toBundle());
        }else {
            if (mNameGameFragment != null) {
                mNameGameFragment.updateAttempts(false);
            }

        }

    }
}

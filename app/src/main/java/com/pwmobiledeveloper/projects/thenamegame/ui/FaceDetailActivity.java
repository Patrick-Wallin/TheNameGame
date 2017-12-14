package com.pwmobiledeveloper.projects.thenamegame.ui;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.pwmobiledeveloper.projects.thenamegame.R;
import com.pwmobiledeveloper.projects.thenamegame.fragment.FaceDetailFragment;
import com.pwmobiledeveloper.projects.thenamegame.model.Person;

import org.parceler.Parcels;

public class FaceDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_detail);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(getString(R.string.person_info))) {
            Parcelable parcelable = intent.getParcelableExtra(getString(R.string.person_info));
            if(parcelable != null) {
                Person person = Parcels.unwrap(parcelable);
                setTitle(person.getFullName());
            }else {
                setTitle(getString(R.string.unknown_person));
            }
            FaceDetailFragment faceDetailFragment = new FaceDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(getString(R.string.person_info), intent.getParcelableExtra(getString(R.string.person_info)));
            faceDetailFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_face_detail_page_container, faceDetailFragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

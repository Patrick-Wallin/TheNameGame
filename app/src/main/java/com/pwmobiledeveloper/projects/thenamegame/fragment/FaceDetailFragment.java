package com.pwmobiledeveloper.projects.thenamegame.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pwmobiledeveloper.projects.thenamegame.R;
import com.pwmobiledeveloper.projects.thenamegame.model.Headshot;
import com.pwmobiledeveloper.projects.thenamegame.model.Person;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FaceDetailFragment extends Fragment {
    @BindView(R.id.person_name_text_view)
    public TextView mPersonNameTextView;
    @BindView(R.id.person_image_view)
    public ImageView mPersonImageView;
    @BindView(R.id.job_title_text_view)
    public TextView mJobTitleTextView;

    private Context mContext;
    private Person mPerson;

    public FaceDetailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_face_detail, container, false);
        ButterKnife.bind(this,view);

        if(mPerson != null) {
            mPersonNameTextView.setText(mPerson.getFullName());

            String imagePath = "";
            Headshot headshot = mPerson.getHeadshot();
            if(headshot != null && headshot.getUrl() != null) {
                imagePath = "http://" + headshot.getUrl();
            }

            boolean hasImagePath = false;

            if(imagePath != null && !imagePath.isEmpty()) {
                hasImagePath = true;
                Picasso.with(mContext)
                        .load(imagePath.toString())
                        .placeholder(R.drawable.ic_face_white_48dp)
                        .into(mPersonImageView);
            }

            if(hasImagePath == false) {
                Picasso.with(mContext)
                        .load(R.drawable.ic_face_white_48dp)
                        .into(mPersonImageView);
            }

            mJobTitleTextView.setText(mPerson.getJobTitle());

        }


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPerson = null;

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            Parcelable parcelable = bundle.getParcelable(getString(R.string.person_info));
            if(parcelable != null) {
                mPerson = Parcels.unwrap(parcelable);
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}

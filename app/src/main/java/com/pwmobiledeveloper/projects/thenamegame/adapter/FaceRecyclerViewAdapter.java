package com.pwmobiledeveloper.projects.thenamegame.adapter;

import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pwmobiledeveloper.projects.thenamegame.R;
import com.pwmobiledeveloper.projects.thenamegame.listener.OnNameGameFragmentInteractionListener;
import com.pwmobiledeveloper.projects.thenamegame.model.Headshot;
import com.pwmobiledeveloper.projects.thenamegame.model.Person;
import com.pwmobiledeveloper.projects.thenamegame.viewholder.FaceViewHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.http.HEAD;

import static android.support.v4.app.ActivityCompat.postponeEnterTransition;

/**
 * Created by piwal on 12/12/2017.
 */

public class FaceRecyclerViewAdapter extends RecyclerView.Adapter<FaceViewHolder> {
    private List<Person> mProfileList;
    private Person mPersonAnswer;
    private Context mContext;
    private final OnNameGameFragmentInteractionListener mListener;

    public FaceRecyclerViewAdapter(List<Person> profileList, Context context, OnNameGameFragmentInteractionListener listener) {
        mProfileList = profileList;
        mContext = context;
        mListener = listener;
    }

    @Override
    public FaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.face_card, parent, false);
        return new FaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FaceViewHolder holder, final int position) {
        if(mProfileList != null && position < mProfileList.size()) {
            String imagePath = "";
            Headshot headshot = mProfileList.get(position).getHeadshot();
            if(headshot != null && headshot.getUrl() != null) {
                imagePath = "http://" + headshot.getUrl();
            }
            ViewCompat.setTransitionName(holder.mFacePhotoImageView, mProfileList.get(position).getId());

            boolean hasImagePath = false;

            if(imagePath != null && !imagePath.isEmpty()) {
                hasImagePath = true;
                Picasso.with(mContext)
                        .load(imagePath.toString())
                        .placeholder(R.drawable.ic_face_white_48dp)
                        .into(holder.mFacePhotoImageView);
            }

            if(hasImagePath == false) {
                Picasso.with(mContext)
                        .load(R.drawable.ic_face_white_48dp)
                        .into(holder.mFacePhotoImageView);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Person person = mProfileList.get(position);
                    String fullName = person.getFirstName() + " " + person.getLastName();
                    mListener.onNameGameFragmentInteraction(mProfileList.get(position), mPersonAnswer.getFullName(), holder.mFacePhotoImageView);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (mProfileList != null ? mProfileList.size() : 0);
    }

    public void loadData(List<Person> itemsPhotosList, Person person) {
        mProfileList = itemsPhotosList;
        mPersonAnswer = person;
        notifyDataSetChanged();
    }
}

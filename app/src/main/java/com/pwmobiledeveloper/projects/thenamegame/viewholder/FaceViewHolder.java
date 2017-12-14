package com.pwmobiledeveloper.projects.thenamegame.viewholder;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.pwmobiledeveloper.projects.thenamegame.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by piwal on 12/12/2017.
 */

public class FaceViewHolder extends RecyclerView.ViewHolder {
    @Nullable
    @BindView(R.id.face_photo_image_view)
    public ImageView mFacePhotoImageView;

    public FaceViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}

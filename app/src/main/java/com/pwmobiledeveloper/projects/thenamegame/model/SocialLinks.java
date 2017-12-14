package com.pwmobiledeveloper.projects.thenamegame.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by piwal on 12/9/2017.
 */

@Parcel
public class SocialLinks {
    @SerializedName("type")
    private String mType;
    @SerializedName("callToAction")
    private String mCallToAction;
    @SerializedName("url")
    private String mUrl;

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getCallToAction() {
        return mCallToAction;
    }

    public void setCallToAction(String callToAction) {
        mCallToAction = callToAction;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}

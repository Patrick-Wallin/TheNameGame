package com.pwmobiledeveloper.projects.thenamegame.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by piwal on 12/9/2017.
 */
@Parcel
public class Headshot {
    @SerializedName("type")
    private String mType;
    @SerializedName("mimeType")
    private String mMimeType;
    @SerializedName("id")
    private String mId;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("alt")
    private String mAlt;
    @SerializedName("height")
    private Integer mHeight;
    @SerializedName("width")
    private Integer mWidth;

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getMimeType() {
        return mMimeType;
    }

    public void setMimeType(String mimeType) {
        mMimeType = mimeType;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getAlt() {
        return mAlt;
    }

    public void setAlt(String alt) {
        mAlt = alt;
    }

    public Integer getHeight() {
        return mHeight;
    }

    public void setHeight(Integer height) {
        mHeight = height;
    }

    public Integer getWidth() {
        return mWidth;
    }

    public void setWidth(Integer width) {
        mWidth = width;
    }
}

package com.pwmobiledeveloper.projects.thenamegame.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by piwal on 12/9/2017.
 */
@Parcel
public class Person {
    @SerializedName("id")
    private String mId;
    @SerializedName("type")
    private String mType;
    @SerializedName("slug")
    private String mSlug;
    @SerializedName("jobTitle")
    private String mJobTitle;
    @SerializedName("firstName")
    private String mFirstName;
    @SerializedName("lastName")
    private String mLastName;
    @SerializedName("headshot")
    private Headshot mHeadshot;
    @SerializedName("socialLinks")
    List<SocialLinks> mSocialLinks;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getSlug() {
        return mSlug;
    }

    public void setSlug(String slug) {
        mSlug = slug;
    }

    public String getJobTitle() {
        return mJobTitle;
    }

    public void setJobTitle(String jobTitle) {
        mJobTitle = jobTitle;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public Headshot getHeadshot() {
        return mHeadshot;
    }

    public void setHeadshot(Headshot headshot) {
        mHeadshot = headshot;
    }

    public List<SocialLinks> getSocialLinks() {
        return mSocialLinks;
    }

    public void setSocialLinks(List<SocialLinks> socialLinks) {
        mSocialLinks = socialLinks;
    }

    public String getFullName() {
        return mFirstName + " " + mLastName;
    }
}

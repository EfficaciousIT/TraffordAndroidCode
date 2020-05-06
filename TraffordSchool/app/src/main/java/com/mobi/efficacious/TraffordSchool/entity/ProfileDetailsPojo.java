package com.mobi.efficacious.TraffordSchool.entity;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileDetailsPojo {

    @SerializedName("ProfileDetails")
    @Expose
    private List<ProfileDetail> profileDetails = null;

    public List<ProfileDetail> getProfileDetails() {
        return profileDetails;
    }

    public void setProfileDetails(List<ProfileDetail> profileDetails) {
        this.profileDetails = profileDetails;
    }

}

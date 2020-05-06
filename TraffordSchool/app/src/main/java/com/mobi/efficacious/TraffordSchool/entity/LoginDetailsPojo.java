package com.mobi.efficacious.TraffordSchool.entity;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginDetailsPojo {

    @SerializedName("LoginDetails")
    @Expose
    private List<LoginDetail> loginDetails = null;

    public List<LoginDetail> getLoginDetails() {
        return loginDetails;
    }

    public void setLoginDetails(List<LoginDetail> loginDetails) {
        this.loginDetails = loginDetails;
    }

}
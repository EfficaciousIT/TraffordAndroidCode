package com.mobi.efficacious.TraffordSchool.entity;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SchoolDetailsPojo {

    @SerializedName("SchoolDetails")
    @Expose
    private List<SchoolDetail> schoolDetails = null;

    public List<SchoolDetail> getSchoolDetails() {
        return schoolDetails;
    }

    public void setSchoolDetails(List<SchoolDetail> schoolDetails) {
        this.schoolDetails = schoolDetails;
    }

}
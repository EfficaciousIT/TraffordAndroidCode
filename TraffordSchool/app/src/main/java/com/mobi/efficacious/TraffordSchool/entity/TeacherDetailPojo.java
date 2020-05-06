package com.mobi.efficacious.TraffordSchool.entity;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherDetailPojo {

    @SerializedName("TeacherDetail")
    @Expose
    private List<TeacherDetail> teacherDetail = null;

    public List<TeacherDetail> getTeacherDetail() {
        return teacherDetail;
    }

    public void setTeacherDetail(List<TeacherDetail> teacherDetail) {
        this.teacherDetail = teacherDetail;
    }

}
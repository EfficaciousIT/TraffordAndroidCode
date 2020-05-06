package com.mobi.efficacious.TraffordSchool.entity;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SyllabusDetailPojo {

    @SerializedName("SyllabusDetail")
    @Expose
    private List<SyllabusDetail> syllabusDetail = null;

    public List<SyllabusDetail> getSyllabusDetail() {
        return syllabusDetail;
    }

    public void setSyllabusDetail(List<SyllabusDetail> syllabusDetail) {
        this.syllabusDetail = syllabusDetail;
    }

}
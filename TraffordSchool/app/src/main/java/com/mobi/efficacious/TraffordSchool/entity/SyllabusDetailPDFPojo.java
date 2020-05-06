package com.mobi.efficacious.TraffordSchool.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SyllabusDetailPDFPojo {

    @SerializedName("SyllabusDetailPDF")
    @Expose
    private List<SyllabusDetailsPDF> syllabusDetailsPDF = null;

    public List<SyllabusDetailsPDF> getSyllabusDetailsPDF() {
        return syllabusDetailsPDF;
    }

    public void setSyllabusDetailsPDF(List<SyllabusDetailsPDF> syllabusDetailsPDFS) {
        this.syllabusDetailsPDF = syllabusDetailsPDFS;
    }

    @Override
    public String toString() {
        return "SyllabusDetailPDFPojo{" +
                "syllabusDetailsPDF=" + syllabusDetailsPDF +
                '}';
    }
}

package com.mobi.efficacious.TraffordSchool.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SyllabusDetailsPDF {

    /*    {
            "SyllabusDetailPDF": [
            {
                "id": 1,
                    "Name": "sample.pdf",
                    "intstandard_id": 3,
                    "intSubject_id": 4,
                    "filePath": "http://eserveshiksha.co.in/SKPSchoolApi/PDF/sample.pdf"
            }
        ]
        }*/
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("Name")
    @Expose
    private String Name;

   @SerializedName("intstandard_id")
    @Expose
    private Integer intstandard_id;

    @SerializedName("intSubject_id")
    @Expose
    private Integer intSubject_id;

    @SerializedName("filePath")
    @Expose
    private String filePath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getIntstandard_id() {
        return intstandard_id;
    }

    public void setIntstandard_id(Integer intstandard_id) {
        this.intstandard_id = intstandard_id;
    }

    public Integer getIntSubject_id() {
        return intSubject_id;
    }

    public void setIntSubject_id(Integer intSubject_id) {
        this.intSubject_id = intSubject_id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


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
        return "SyllabusDetailsPDF{" +
                "id=" + id +
                ", Name=" + Name +
                ", intstandard_id=" + intstandard_id +
                ", intSubject_id=" + intSubject_id +
                ", filePath=" + filePath +
                '}';
    }
}

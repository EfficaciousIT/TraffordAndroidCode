package com.mobi.efficacious.TraffordSchool.entity;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttendanceDetailPojo {

    @SerializedName("AttendanceDetail")
    @Expose
    private List<AttendanceDetail> attendanceDetail = null;

    public List<AttendanceDetail> getAttendanceDetail() {
        return attendanceDetail;
    }

    public void setAttendanceDetail(List<AttendanceDetail> attendanceDetail) {
        this.attendanceDetail = attendanceDetail;
    }

}

package com.mobi.efficacious.TraffordSchool.entity;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventDetailPojo {

    @SerializedName("EventDetail")
    @Expose
    private List<EventDetail> eventDetail = null;

    public List<EventDetail> getEventDetail() {
        return eventDetail;
    }

    public void setEventDetail(List<EventDetail> eventDetail) {
        this.eventDetail = eventDetail;
    }

}
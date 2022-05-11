
package com.ominfo.hra_app.ui.attendance.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class UpdateAttendanceRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("start_time")
    private RequestBody startTime;

    @SerializedName("end_time")
    private RequestBody endTime;

    @SerializedName("id")
    private RequestBody id;

    @SerializedName("end_longitude")
    private RequestBody endLongitude;

    @SerializedName("end_latitude")
    private RequestBody endLatitude;

    public RequestBody getAction() {
        return action;
    }

    public void setAction(RequestBody action) {
        this.action = action;
    }

    public RequestBody getStartTime() {
        return startTime;
    }

    public void setStartTime(RequestBody startTime) {
        this.startTime = startTime;
    }

    public RequestBody getEndTime() {
        return endTime;
    }

    public void setEndTime(RequestBody endTime) {
        this.endTime = endTime;
    }

    public RequestBody getId() {
        return id;
    }

    public void setId(RequestBody id) {
        this.id = id;
    }

    public RequestBody getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(RequestBody endLongitude) {
        this.endLongitude = endLongitude;
    }

    public RequestBody getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(RequestBody endLatitude) {
        this.endLatitude = endLatitude;
    }
}

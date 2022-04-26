
package com.ominfo.crm_solution.ui.attendance.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LocationPerHourRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("emp_id")
    private RequestBody empId;

    @SerializedName("date")
    private RequestBody date;

    @SerializedName("longitude")
    private RequestBody longitude;

    @SerializedName("latitude")
    private RequestBody latitude;

    @SerializedName("startTime")
    private RequestBody startTime;

    @SerializedName("requested_token")
    private RequestBody requestedToken;

    public RequestBody getAction() {
        return action;
    }

    public void setAction(RequestBody action) {
        this.action = action;
    }

    public RequestBody getEmpId() {
        return empId;
    }

    public void setEmpId(RequestBody empId) {
        this.empId = empId;
    }

    public RequestBody getDate() {
        return date;
    }

    public void setDate(RequestBody date) {
        this.date = date;
    }

    public RequestBody getLongitude() {
        return longitude;
    }

    public void setLongitude(RequestBody longitude) {
        this.longitude = longitude;
    }

    public RequestBody getLatitude() {
        return latitude;
    }

    public void setLatitude(RequestBody latitude) {
        this.latitude = latitude;
    }

    public RequestBody getStartTime() {
        return startTime;
    }

    public void setStartTime(RequestBody startTime) {
        this.startTime = startTime;
    }

    public RequestBody getRequestedToken() {
        return requestedToken;
    }

    public void setRequestedToken(RequestBody requestedToken) {
        this.requestedToken = requestedToken;
    }
}

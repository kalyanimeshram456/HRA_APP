
package com.ominfo.crm_solution.ui.attendance.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class MarkAttendanceRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("emp_id")
    private RequestBody empId;

    @SerializedName("date")
    private RequestBody date;

    @SerializedName("start_time")
    private RequestBody startTime;

    @SerializedName("start_longitude")
    private RequestBody startLongitude;

    @SerializedName("start_latitude")
    private RequestBody startLatitude;

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

    public RequestBody getStartTime() {
        return startTime;
    }

    public void setStartTime(RequestBody startTime) {
        this.startTime = startTime;
    }

    public RequestBody getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(RequestBody startLongitude) {
        this.startLongitude = startLongitude;
    }

    public RequestBody getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(RequestBody startLatitude) {
        this.startLatitude = startLatitude;
    }
}

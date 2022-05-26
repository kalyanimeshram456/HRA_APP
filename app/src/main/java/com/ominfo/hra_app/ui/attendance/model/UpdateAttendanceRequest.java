
package com.ominfo.hra_app.ui.attendance.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class UpdateAttendanceRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("emp_id")
    private RequestBody emp_id;

    @SerializedName("date")
    private RequestBody date;

    @SerializedName("start_time")
    private RequestBody start_time;

    @SerializedName("start_longitude")
    private RequestBody start_longitude;

    @SerializedName("start_latitude")
    private RequestBody start_latitude;

    @SerializedName("office_start_addr")
    private RequestBody office_start_addr;

    @SerializedName("is_late")
    private RequestBody is_late;

    @SerializedName("office_end_addr")
    private RequestBody office_end_addr;

    @SerializedName("end_time")
    private RequestBody end_time;

    @SerializedName("end_longitude")
    private RequestBody end_longitude;

    @SerializedName("end_latitude")
    private RequestBody end_latitude;

    public RequestBody getAction() {
        return action;
    }

    public void setAction(RequestBody action) {
        this.action = action;
    }

    public RequestBody getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(RequestBody emp_id) {
        this.emp_id = emp_id;
    }

    public RequestBody getDate() {
        return date;
    }

    public void setDate(RequestBody date) {
        this.date = date;
    }

    public RequestBody getStart_time() {
        return start_time;
    }

    public void setStart_time(RequestBody start_time) {
        this.start_time = start_time;
    }

    public RequestBody getStart_longitude() {
        return start_longitude;
    }

    public void setStart_longitude(RequestBody start_longitude) {
        this.start_longitude = start_longitude;
    }

    public RequestBody getStart_latitude() {
        return start_latitude;
    }

    public void setStart_latitude(RequestBody start_latitude) {
        this.start_latitude = start_latitude;
    }

    public RequestBody getOffice_start_addr() {
        return office_start_addr;
    }

    public void setOffice_start_addr(RequestBody office_start_addr) {
        this.office_start_addr = office_start_addr;
    }

    public RequestBody getIs_late() {
        return is_late;
    }

    public void setIs_late(RequestBody is_late) {
        this.is_late = is_late;
    }

    public RequestBody getOffice_end_addr() {
        return office_end_addr;
    }

    public void setOffice_end_addr(RequestBody office_end_addr) {
        this.office_end_addr = office_end_addr;
    }

    public RequestBody getEnd_time() {
        return end_time;
    }

    public void setEnd_time(RequestBody end_time) {
        this.end_time = end_time;
    }

    public RequestBody getEnd_longitude() {
        return end_longitude;
    }

    public void setEnd_longitude(RequestBody end_longitude) {
        this.end_longitude = end_longitude;
    }

    public RequestBody getEnd_latitude() {
        return end_latitude;
    }

    public void setEnd_latitude(RequestBody end_latitude) {
        this.end_latitude = end_latitude;
    }
}

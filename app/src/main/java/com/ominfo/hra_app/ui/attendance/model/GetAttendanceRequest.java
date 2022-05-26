
package com.ominfo.hra_app.ui.attendance.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GetAttendanceRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("emp_id")
    private RequestBody empId;

    @SerializedName("frm_date")
    private RequestBody frm_date;

    @SerializedName("to_date")
    private RequestBody to_date;

    @SerializedName("token")
    private RequestBody token;

    @SerializedName("company_ID")
    private RequestBody company_ID;

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

    public RequestBody getFrm_date() {
        return frm_date;
    }

    public void setFrm_date(RequestBody frm_date) {
        this.frm_date = frm_date;
    }

    public RequestBody getTo_date() {
        return to_date;
    }

    public void setTo_date(RequestBody to_date) {
        this.to_date = to_date;
    }

    public RequestBody getToken() {
        return token;
    }

    public void setToken(RequestBody token) {
        this.token = token;
    }

    public RequestBody getCompany_ID() {
        return company_ID;
    }

    public void setCompany_ID(RequestBody company_ID) {
        this.company_ID = company_ID;
    }
}

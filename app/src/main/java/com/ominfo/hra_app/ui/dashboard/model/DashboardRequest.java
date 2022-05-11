
package com.ominfo.hra_app.ui.dashboard.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class DashboardRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("employee")
    private RequestBody employee;

    @SerializedName("company_id")
    private RequestBody companyId;

    @SerializedName("start_date")
    private RequestBody startDate;

    @SerializedName("end_date")
    private RequestBody endDate;

    public RequestBody getAction() {
        return action;
    }

    public void setAction(RequestBody action) {
        this.action = action;
    }

    public RequestBody getEmployee() {
        return employee;
    }

    public void setEmployee(RequestBody employee) {
        this.employee = employee;
    }

    public RequestBody getCompanyId() {
        return companyId;
    }

    public void setCompanyId(RequestBody companyId) {
        this.companyId = companyId;
    }

    public RequestBody getStartDate() {
        return startDate;
    }

    public void setStartDate(RequestBody startDate) {
        this.startDate = startDate;
    }

    public RequestBody getEndDate() {
        return endDate;
    }

    public void setEndDate(RequestBody endDate) {
        this.endDate = endDate;
    }
}

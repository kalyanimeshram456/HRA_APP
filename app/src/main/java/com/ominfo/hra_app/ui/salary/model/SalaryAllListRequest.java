
package com.ominfo.hra_app.ui.salary.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SalaryAllListRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("company_id")
    private RequestBody companyId;

    @SerializedName("employee")
    private RequestBody employee;

    @SerializedName("token")
    private RequestBody token;

    @SerializedName("page_number")
    private RequestBody pageNumber;

    @SerializedName("page_size")
    private RequestBody pageSize;

    @SerializedName("filter_emp_name")
    private RequestBody filterEmpName;

    @SerializedName("filter_emp_position")
    private RequestBody filterEmpPosition;

    @SerializedName("filter_emp_isActive")
    private RequestBody filterEmpIsActive;

    public RequestBody getAction() {
        return action;
    }

    public void setAction(RequestBody action) {
        this.action = action;
    }

    public RequestBody getCompanyId() {
        return companyId;
    }

    public void setCompanyId(RequestBody companyId) {
        this.companyId = companyId;
    }

    public RequestBody getEmployee() {
        return employee;
    }

    public void setEmployee(RequestBody employee) {
        this.employee = employee;
    }

    public RequestBody getToken() {
        return token;
    }

    public void setToken(RequestBody token) {
        this.token = token;
    }

    public RequestBody getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(RequestBody pageNumber) {
        this.pageNumber = pageNumber;
    }

    public RequestBody getPageSize() {
        return pageSize;
    }

    public void setPageSize(RequestBody pageSize) {
        this.pageSize = pageSize;
    }

    public RequestBody getFilterEmpName() {
        return filterEmpName;
    }

    public void setFilterEmpName(RequestBody filterEmpName) {
        this.filterEmpName = filterEmpName;
    }

    public RequestBody getFilterEmpPosition() {
        return filterEmpPosition;
    }

    public void setFilterEmpPosition(RequestBody filterEmpPosition) {
        this.filterEmpPosition = filterEmpPosition;
    }

    public RequestBody getFilterEmpIsActive() {
        return filterEmpIsActive;
    }

    public void setFilterEmpIsActive(RequestBody filterEmpIsActive) {
        this.filterEmpIsActive = filterEmpIsActive;
    }
}

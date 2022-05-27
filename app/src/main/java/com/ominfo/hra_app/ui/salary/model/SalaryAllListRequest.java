
package com.ominfo.hra_app.ui.salary.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SalaryAllListRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("company_ID")
    private RequestBody company_ID;

    @SerializedName("page_number")
    private RequestBody pageNumber;

    @SerializedName("page_size")
    private RequestBody pageSize;

    @SerializedName("isAdmin")
    private RequestBody isAdmin;

    @SerializedName("month")
    private RequestBody month;

    @SerializedName("year")
    private RequestBody year;

    @SerializedName("emp_id")
    private RequestBody emp_id;

    public RequestBody getYear() {
        return year;
    }

    public void setYear(RequestBody year) {
        this.year = year;
    }

    public RequestBody getMonth() {
        return month;
    }

    public void setMonth(RequestBody month) {
        this.month = month;
    }

    public RequestBody getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(RequestBody emp_id) {
        this.emp_id = emp_id;
    }

    public RequestBody getAction() {
        return action;
    }

    public void setAction(RequestBody action) {
        this.action = action;
    }

    public RequestBody getCompany_ID() {
        return company_ID;
    }

    public void setCompany_ID(RequestBody company_ID) {
        this.company_ID = company_ID;
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

    public RequestBody getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(RequestBody isAdmin) {
        this.isAdmin = isAdmin;
    }
}

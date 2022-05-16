
package com.ominfo.hra_app.ui.leave.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class PastLeaveListRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("emp_id")
    private RequestBody empId;

    @SerializedName("month")
    private RequestBody month;

    @SerializedName("pageno")
    private RequestBody pageNo;

    @SerializedName("pagesize")
    private RequestBody pageSize;

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

    public RequestBody getMonth() {
        return month;
    }

    public void setMonth(RequestBody month) {
        this.month = month;
    }

    public RequestBody getPageNo() {
        return pageNo;
    }

    public void setPageNo(RequestBody pageNo) {
        this.pageNo = pageNo;
    }

    public RequestBody getPageSize() {
        return pageSize;
    }

    public void setPageSize(RequestBody pageSize) {
        this.pageSize = pageSize;
    }
}

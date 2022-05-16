
package com.ominfo.hra_app.ui.leave.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AcceptRejectListRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("emp_id")
    private RequestBody empId;

    @SerializedName("leave_type")
    private RequestBody leaveType;

    @SerializedName("from_date")
    private RequestBody fromDate;

    @SerializedName("end_date")
    private RequestBody endDate;

    @SerializedName("pageno")
    private RequestBody pageNo;

    @SerializedName("pagesize")
    private RequestBody pageSize;

    @SerializedName("searched_emp")
    private RequestBody searchedEmp;


    public RequestBody getSearchedEmp() {
        return searchedEmp;
    }

    public void setSearchedEmp(RequestBody searchedEmp) {
        this.searchedEmp = searchedEmp;
    }


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

    public RequestBody getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(RequestBody leaveType) {
        this.leaveType = leaveType;
    }

    public RequestBody getFromDate() {
        return fromDate;
    }

    public void setFromDate(RequestBody fromDate) {
        this.fromDate = fromDate;
    }

    public RequestBody getEndDate() {
        return endDate;
    }

    public void setEndDate(RequestBody endDate) {
        this.endDate = endDate;
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

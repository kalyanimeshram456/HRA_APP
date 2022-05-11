
package com.ominfo.hra_app.ui.my_account.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LeaveApplicationRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("emp_id")
    private RequestBody empId;

    @SerializedName("pageno")
    private RequestBody pageno;

    @SerializedName("pagesize")
    private RequestBody pagesize;

    @SerializedName("leave_type")
    private RequestBody leaveType;

    @SerializedName("status")
    private RequestBody status;

    @SerializedName("from_date")
    private RequestBody fromDate;

    @SerializedName("end_date")
    private RequestBody endDate;

    public RequestBody getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(RequestBody leaveType) {
        this.leaveType = leaveType;
    }

    public RequestBody getStatus() {
        return status;
    }

    public void setStatus(RequestBody status) {
        this.status = status;
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

    public RequestBody getPageno() {
        return pageno;
    }

    public void setPageno(RequestBody pageno) {
        this.pageno = pageno;
    }

    public RequestBody getPagesize() {
        return pagesize;
    }

    public void setPagesize(RequestBody pagesize) {
        this.pagesize = pagesize;
    }
}

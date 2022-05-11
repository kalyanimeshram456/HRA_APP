
package com.ominfo.hra_app.ui.my_account.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ApplyLeaveRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("emp_id")
    private RequestBody empId;

    @SerializedName("duration")
    private RequestBody duration;

    @SerializedName("start_time")
    private RequestBody startTime;

    @SerializedName("end_time")
    private RequestBody endTime;

    @SerializedName("leave_type")
    private RequestBody leaveType;

    @SerializedName("comment")
    private RequestBody comment;

    @SerializedName("leave_status")
    private RequestBody leaveStatus;

    @SerializedName("updated_by")
    private RequestBody updatedBy;

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

    public RequestBody getDuration() {
        return duration;
    }

    public void setDuration(RequestBody duration) {
        this.duration = duration;
    }

    public RequestBody getStartTime() {
        return startTime;
    }

    public void setStartTime(RequestBody startTime) {
        this.startTime = startTime;
    }

    public RequestBody getEndTime() {
        return endTime;
    }

    public void setEndTime(RequestBody endTime) {
        this.endTime = endTime;
    }

    public RequestBody getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(RequestBody leaveType) {
        this.leaveType = leaveType;
    }

    public RequestBody getComment() {
        return comment;
    }

    public void setComment(RequestBody comment) {
        this.comment = comment;
    }

    public RequestBody getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(RequestBody leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public RequestBody getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(RequestBody updatedBy) {
        this.updatedBy = updatedBy;
    }
}

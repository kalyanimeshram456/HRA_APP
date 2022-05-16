
package com.ominfo.hra_app.ui.leave.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import okhttp3.RequestBody;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LeaveStatusRequest {

    @SerializedName("action")
    private RequestBody action;

    @SerializedName("id")
    private RequestBody id;

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

    public RequestBody getId() {
        return id;
    }

    public void setId(RequestBody id) {
        this.id = id;
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

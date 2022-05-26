
package com.ominfo.hra_app.ui.leave.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class EmployeeLeave {

    @SerializedName("created_on")
    private String mCreatedOn;
    @SerializedName("date")
    private String mDate;
    @SerializedName("emp_id")
    private String mEmpId;
    @SerializedName("leave_type")
    private String mLeaveType;
    @SerializedName("record_id")
    private String mRecordId;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("updated_on")
    private String mUpdatedOn;

    public String getCreatedOn() {
        return mCreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        mCreatedOn = createdOn;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getEmpId() {
        return mEmpId;
    }

    public void setEmpId(String empId) {
        mEmpId = empId;
    }

    public String getLeaveType() {
        return mLeaveType;
    }

    public void setLeaveType(String leaveType) {
        mLeaveType = leaveType;
    }

    public String getRecordId() {
        return mRecordId;
    }

    public void setRecordId(String recordId) {
        mRecordId = recordId;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getUpdatedOn() {
        return mUpdatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        mUpdatedOn = updatedOn;
    }

}

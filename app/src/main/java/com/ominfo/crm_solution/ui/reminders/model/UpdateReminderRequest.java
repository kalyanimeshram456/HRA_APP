
package com.ominfo.crm_solution.ui.reminders.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class UpdateReminderRequest {

    @SerializedName("companyId")
    private String mCompanyId;
    @SerializedName("date")
    private String mDate;
    @SerializedName("employeeId")
    private String mEmployeeId;
    @SerializedName("record_id")
    private String mRecordId;
    @SerializedName("requestType")
    private String mRequestType;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("time")
    private String mTime;

    public String getCompanyId() {
        return mCompanyId;
    }

    public void setCompanyId(String companyId) {
        mCompanyId = companyId;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getEmployeeId() {
        return mEmployeeId;
    }

    public void setEmployeeId(String employeeId) {
        mEmployeeId = employeeId;
    }

    public String getRecordId() {
        return mRecordId;
    }

    public void setRecordId(String recordId) {
        mRecordId = recordId;
    }

    public String getRequestType() {
        return mRequestType;
    }

    public void setRequestType(String requestType) {
        mRequestType = requestType;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

}

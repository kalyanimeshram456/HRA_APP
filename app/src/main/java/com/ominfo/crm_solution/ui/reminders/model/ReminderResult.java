
package com.ominfo.crm_solution.ui.reminders.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ReminderResult {

    @SerializedName("company_ID")
    private Long mCompanyID;
    @SerializedName("created_by")
    private String mCreatedBy;
    @SerializedName("created_for")
    private Long mCreatedFor;
    @SerializedName("created_on")
    private String mCreatedOn;
    @SerializedName("record_id")
    private Long mRecordId;
    @SerializedName("rem_date")
    private String mRemDate;
    @SerializedName("rem_description")
    private String mRemDescription;
    @SerializedName("rem_status")
    private String mRemStatus;
    @SerializedName("rem_time")
    private String mRemTime;
    @SerializedName("remark")
    private String mRemark;
    @SerializedName("updated_by")
    private Long mUpdatedBy;
    @SerializedName("updated_on")
    private String mUpdatedOn;
    @SerializedName("DT")
    private String newDateReminder;

    public String getNewDateReminder() {
        return newDateReminder;
    }

    public void setNewDateReminder(String newDateReminder) {
        this.newDateReminder = newDateReminder;
    }

    public Long getCompanyID() {
        return mCompanyID;
    }

    public void setCompanyID(Long companyID) {
        mCompanyID = companyID;
    }

    public String getCreatedBy() {
        return mCreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        mCreatedBy = createdBy;
    }

    public Long getCreatedFor() {
        return mCreatedFor;
    }

    public void setCreatedFor(Long createdFor) {
        mCreatedFor = createdFor;
    }

    public String getCreatedOn() {
        return mCreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        mCreatedOn = createdOn;
    }

    public Long getRecordId() {
        return mRecordId;
    }

    public void setRecordId(Long recordId) {
        mRecordId = recordId;
    }

    public String getRemDate() {
        return mRemDate;
    }

    public void setRemDate(String remDate) {
        mRemDate = remDate;
    }

    public String getRemDescription() {
        return mRemDescription;
    }

    public void setRemDescription(String remDescription) {
        mRemDescription = remDescription;
    }

    public String getRemStatus() {
        return mRemStatus;
    }

    public void setRemStatus(String remStatus) {
        mRemStatus = remStatus;
    }

    public String getRemTime() {
        return mRemTime;
    }

    public void setRemTime(String remTime) {
        mRemTime = remTime;
    }

    public String getRemark() {
        return mRemark;
    }

    public void setRemark(String remark) {
        mRemark = remark;
    }

    public Long getUpdatedBy() {
        return mUpdatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        mUpdatedBy = updatedBy;
    }

    public String getUpdatedOn() {
        return mUpdatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        mUpdatedOn = updatedOn;
    }

}

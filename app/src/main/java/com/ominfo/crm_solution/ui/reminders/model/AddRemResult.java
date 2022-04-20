
package com.ominfo.crm_solution.ui.reminders.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AddRemResult {


    @SerializedName("record_id")
    private Long mRecordId;
    @SerializedName("rem_description")
    private String mRemDescription;
    @SerializedName("rem_time")
    private String mRemTime;
    @SerializedName("DT")
    private String newDateReminder;

    public String getNewDateReminder() {
        return newDateReminder;
    }

    public void setNewDateReminder(String newDateReminder) {
        this.newDateReminder = newDateReminder;
    }

    public Long getRecordId() {
        return mRecordId;
    }

    public void setRecordId(Long recordId) {
        mRecordId = recordId;
    }

    public String getRemDescription() {
        return mRemDescription;
    }

    public void setRemDescription(String remDescription) {
        mRemDescription = remDescription;
    }

    public String getRemTime() {
        return mRemTime;
    }

    public void setRemTime(String remTime) {
        mRemTime = remTime;
    }

}

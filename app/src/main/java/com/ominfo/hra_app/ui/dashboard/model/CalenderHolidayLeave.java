
package com.ominfo.hra_app.ui.dashboard.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class CalenderHolidayLeave {

    @SerializedName("company_id")
    private String mCompanyId;
    @SerializedName("created_on")
    private Object mCreatedOn;
    @SerializedName("date")
    private String mDate;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("name")
    private String mName;
    @SerializedName("record_id")
    private String mRecordId;
    @SerializedName("updated_on")
    private Object mUpdatedOn;

    public String getCompanyId() {
        return mCompanyId;
    }

    public void setCompanyId(String companyId) {
        mCompanyId = companyId;
    }

    public Object getCreatedOn() {
        return mCreatedOn;
    }

    public void setCreatedOn(Object createdOn) {
        mCreatedOn = createdOn;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getRecordId() {
        return mRecordId;
    }

    public void setRecordId(String recordId) {
        mRecordId = recordId;
    }

    public Object getUpdatedOn() {
        return mUpdatedOn;
    }

    public void setUpdatedOn(Object updatedOn) {
        mUpdatedOn = updatedOn;
    }

}

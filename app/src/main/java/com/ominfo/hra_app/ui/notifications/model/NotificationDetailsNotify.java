
package com.ominfo.hra_app.ui.notifications.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class NotificationDetailsNotify {

    @SerializedName("company_ID")
    private String mCompanyID;
    @SerializedName("created_on")
    private String mCreatedOn;
    @SerializedName("expiry")
    private String mExpiry;
    @SerializedName("heading")
    private String mHeading;
    @SerializedName("is_loaded")
    private String mIsLoaded;
    @SerializedName("is_viewed")
    private String mIsViewed;
    @SerializedName("notif_text")
    private String mNotifText;
    @SerializedName("record_id")
    private String mRecordId;
    @SerializedName("related_id")
    private String mRelatedId;
    @SerializedName("rm_id")
    private Object mRmId;
    @SerializedName("type")
    private String mType;
    @SerializedName("updated_on")
    private String mUpdatedOn;

    public String getCompanyID() {
        return mCompanyID;
    }

    public void setCompanyID(String companyID) {
        mCompanyID = companyID;
    }

    public String getCreatedOn() {
        return mCreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        mCreatedOn = createdOn;
    }

    public String getExpiry() {
        return mExpiry;
    }

    public void setExpiry(String expiry) {
        mExpiry = expiry;
    }

    public String getHeading() {
        return mHeading;
    }

    public void setHeading(String heading) {
        mHeading = heading;
    }

    public String getIsLoaded() {
        return mIsLoaded;
    }

    public void setIsLoaded(String isLoaded) {
        mIsLoaded = isLoaded;
    }

    public String getIsViewed() {
        return mIsViewed;
    }

    public void setIsViewed(String isViewed) {
        mIsViewed = isViewed;
    }

    public String getNotifText() {
        return mNotifText;
    }

    public void setNotifText(String notifText) {
        mNotifText = notifText;
    }

    public String getRecordId() {
        return mRecordId;
    }

    public void setRecordId(String recordId) {
        mRecordId = recordId;
    }

    public String getRelatedId() {
        return mRelatedId;
    }

    public void setRelatedId(String relatedId) {
        mRelatedId = relatedId;
    }

    public Object getRmId() {
        return mRmId;
    }

    public void setRmId(Object rmId) {
        mRmId = rmId;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getUpdatedOn() {
        return mUpdatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        mUpdatedOn = updatedOn;
    }

}

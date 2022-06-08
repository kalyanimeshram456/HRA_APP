
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
    private String mRmId;
    @SerializedName("type")
    private String mType;
    @SerializedName("updated_on")
    private String mUpdatedOn;
    @SerializedName("start_time")
    private String startTime;
    @SerializedName("stnd_start_tym")
    private String stnd_start_tym;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStnd_start_tym() {
        return stnd_start_tym;
    }

    public void setStnd_start_tym(String stnd_start_tym) {
        this.stnd_start_tym = stnd_start_tym;
    }

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

    public String getRmId() {
        return mRmId;
    }

    public void setRmId(String rmId) {
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

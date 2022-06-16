
package com.ominfo.hra_app.ui.notifications.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class NotificationResult {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("notifdata")
    private List<NotificationData> mNotifdata;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("isActive")
    private String isActive;

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<NotificationData> getNotifdata() {
        return mNotifdata;
    }

    public void setNotifdata(List<NotificationData> notifdata) {
        mNotifdata = notifdata;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}

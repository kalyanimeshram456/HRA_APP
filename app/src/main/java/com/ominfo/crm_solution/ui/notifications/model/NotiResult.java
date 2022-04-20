
package com.ominfo.crm_solution.ui.notifications.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class NotiResult {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("notifdata")
    private List<NotificationResult> mNotifdata;
    @SerializedName("status")
    private String mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<NotificationResult> getNotifdata() {
        return mNotifdata;
    }

    public void setNotifdata(List<NotificationResult> notifdata) {
        mNotifdata = notifdata;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}

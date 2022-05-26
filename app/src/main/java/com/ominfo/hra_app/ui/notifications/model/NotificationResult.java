
package com.ominfo.hra_app.ui.notifications.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class NotificationResult {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("notify")
    private List<NotificationNotify> mNotify;
    @SerializedName("status")
    private String mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<NotificationNotify> getNotify() {
        return mNotify;
    }

    public void setNotify(List<NotificationNotify> notify) {
        mNotify = notify;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}

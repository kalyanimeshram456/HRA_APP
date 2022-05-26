
package com.ominfo.hra_app.ui.notifications.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class NotificationResponse {

    @SerializedName("result")
    private NotificationResult mResult;

    public NotificationResult getResult() {
        return mResult;
    }

    public void setResult(NotificationResult result) {
        mResult = result;
    }

}

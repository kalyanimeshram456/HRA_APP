
package com.ominfo.hra_app.ui.notifications.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class NotificationDetailsResponse {

    @SerializedName("result")
    private NotificationDetailsResult mResult;

    public NotificationDetailsResult getResult() {
        return mResult;
    }

    public void setResult(NotificationDetailsResult result) {
        mResult = result;
    }

}

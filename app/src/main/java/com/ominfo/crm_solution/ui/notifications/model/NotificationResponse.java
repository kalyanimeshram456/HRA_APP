
package com.ominfo.crm_solution.ui.notifications.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class NotificationResponse {

    @SerializedName("result")
    private NotiResult mResult;

    public NotiResult getResult() {
        return mResult;
    }

    public void setResult(NotiResult result) {
        mResult = result;
    }

}

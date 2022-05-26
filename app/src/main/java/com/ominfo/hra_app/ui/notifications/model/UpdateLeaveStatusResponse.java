
package com.ominfo.hra_app.ui.notifications.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class UpdateLeaveStatusResponse {

    @SerializedName("result")
    private UpdateLeaveResult mResult;

    public UpdateLeaveResult getResult() {
        return mResult;
    }

    public void setResult(UpdateLeaveResult result) {
        mResult = result;
    }

}

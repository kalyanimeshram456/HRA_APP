
package com.ominfo.crm_solution.ui.notifications.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class DeleteNotificationResponse {

    @SerializedName("result")
    private Result mResult;

    public Result getResult() {
        return mResult;
    }

    public void setResult(Result result) {
        mResult = result;
    }

}
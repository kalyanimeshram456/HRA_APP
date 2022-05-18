
package com.ominfo.hra_app.ui.leave.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LeaveCountResponse {

    @SerializedName("result")
    private LeaveCountResult mResult;

    public LeaveCountResult getResult() {
        return mResult;
    }

    public void setResult(LeaveCountResult result) {
        mResult = result;
    }

}


package com.ominfo.hra_app.ui.leave.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LeaveStatusResponse {

    @SerializedName("result")
    private LeaveStatusResult mResult;

    public LeaveStatusResult getResult() {
        return mResult;
    }

    public void setResult(LeaveStatusResult result) {
        mResult = result;
    }

}

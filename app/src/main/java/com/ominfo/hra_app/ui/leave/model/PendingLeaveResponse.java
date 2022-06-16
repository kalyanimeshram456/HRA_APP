
package com.ominfo.hra_app.ui.leave.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class PendingLeaveResponse {

    @SerializedName("result")
    private PendingLeaveResult mResult;

    public PendingLeaveResult getResult() {
        return mResult;
    }

    public void setResult(PendingLeaveResult result) {
        mResult = result;
    }

}

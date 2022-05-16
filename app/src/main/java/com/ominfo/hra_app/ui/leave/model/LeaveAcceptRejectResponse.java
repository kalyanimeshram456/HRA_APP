
package com.ominfo.hra_app.ui.leave.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LeaveAcceptRejectResponse {

    @SerializedName("result")
    private AcceptRejectResult mResult;

    public AcceptRejectResult getResult() {
        return mResult;
    }

    public void setResult(AcceptRejectResult result) {
        mResult = result;
    }

}

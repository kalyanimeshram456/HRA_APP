
package com.ominfo.hra_app.ui.leave.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class PastLeaveListResponse {

    @SerializedName("result")
    private PastLeaveResult mResult;

    public PastLeaveResult getResult() {
        return mResult;
    }

    public void setResult(PastLeaveResult result) {
        mResult = result;
    }

}

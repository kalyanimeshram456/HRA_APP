
package com.ominfo.hra_app.ui.payment.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class RenewPlanResponse {

    @SerializedName("result")
    private RenewPlanResult mResult;

    public RenewPlanResult getResult() {
        return mResult;
    }

    public void setResult(RenewPlanResult result) {
        mResult = result;
    }

}


package com.ominfo.hra_app.ui.payment.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class PayPlanResponse {

    @SerializedName("result")
    private PayPlanResult mResult;

    public PayPlanResult getResult() {
        return mResult;
    }

    public void setResult(PayPlanResult result) {
        mResult = result;
    }

}

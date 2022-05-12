
package com.ominfo.hra_app.ui.registration.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SubscriptionResponse {

    @SerializedName("result")
    private SubscriptionResult mResult;

    public SubscriptionResult getResult() {
        return mResult;
    }

    public void setResult(SubscriptionResult result) {
        mResult = result;
    }

}

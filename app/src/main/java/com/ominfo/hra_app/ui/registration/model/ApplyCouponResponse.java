
package com.ominfo.hra_app.ui.registration.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ApplyCouponResponse {

    @SerializedName("result")
    private ApplyCouponResult mResult;

    public ApplyCouponResult getResult() {
        return mResult;
    }

    public void setResult(ApplyCouponResult result) {
        mResult = result;
    }

}

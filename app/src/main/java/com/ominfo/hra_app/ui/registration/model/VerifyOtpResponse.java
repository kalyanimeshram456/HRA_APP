
package com.ominfo.hra_app.ui.registration.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class VerifyOtpResponse {

    @SerializedName("result")
    private VerifyOtpResult mResult;

    public VerifyOtpResult getResult() {
        return mResult;
    }

    public void setResult(VerifyOtpResult result) {
        mResult = result;
    }

}

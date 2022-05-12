
package com.ominfo.hra_app.ui.registration.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class CheckPrefixResponse {

    @SerializedName("result")
    private CheckPrefixResult mResult;

    public CheckPrefixResult getResult() {
        return mResult;
    }

    public void setResult(CheckPrefixResult result) {
        mResult = result;
    }

}

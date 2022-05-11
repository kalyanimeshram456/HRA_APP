
package com.ominfo.hra_app.ui.login.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LogoutResponse {

    @SerializedName("result")
    private LogoutResult mResult;

    public LogoutResult getResult() {
        return mResult;
    }

    public void setResult(LogoutResult result) {
        mResult = result;
    }

}

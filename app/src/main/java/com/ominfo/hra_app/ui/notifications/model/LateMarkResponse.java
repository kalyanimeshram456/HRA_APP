
package com.ominfo.hra_app.ui.notifications.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LateMarkResponse {

    @SerializedName("result")
    private LateMarkResult mResult;

    public LateMarkResult getResult() {
        return mResult;
    }

    public void setResult(LateMarkResult result) {
        mResult = result;
    }

}

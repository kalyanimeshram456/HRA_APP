
package com.ominfo.hra_app.ui.notifications.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AbsentMarkResponse {

    @SerializedName("result")
    private AbsentMarkResult mResult;

    public AbsentMarkResult getResult() {
        return mResult;
    }

    public void setResult(AbsentMarkResult result) {
        mResult = result;
    }

}


package com.ominfo.hra_app.ui.dashboard.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GetBirthDayResponse {

    @SerializedName("result")
    private BirthDayResult mResult;

    public BirthDayResult getResult() {
        return mResult;
    }

    public void setResult(BirthDayResult result) {
        mResult = result;
    }

}

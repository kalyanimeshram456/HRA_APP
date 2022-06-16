
package com.ominfo.hra_app.ui.employees.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LeavingDateResponse {

    @SerializedName("result")
    private LeavingDateResult mResult;

    public LeavingDateResult getResult() {
        return mResult;
    }

    public void setResult(LeavingDateResult result) {
        mResult = result;
    }

}

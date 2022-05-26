
package com.ominfo.hra_app.ui.leave.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ActiveEmployeeListResponse {

    @SerializedName("result")
    private ActiveEmployeeListResult mResult;

    public ActiveEmployeeListResult getResult() {
        return mResult;
    }

    public void setResult(ActiveEmployeeListResult result) {
        mResult = result;
    }

}

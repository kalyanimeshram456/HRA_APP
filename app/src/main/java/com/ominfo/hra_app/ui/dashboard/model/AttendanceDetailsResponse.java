
package com.ominfo.hra_app.ui.dashboard.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AttendanceDetailsResponse {

    @SerializedName("result")
    private AttendanceDetailsResult mResult;

    public AttendanceDetailsResult getResult() {
        return mResult;
    }

    public void setResult(AttendanceDetailsResult result) {
        mResult = result;
    }

}


package com.ominfo.hra_app.ui.attendance.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class MarkAttendanceResponse {

    @SerializedName("result")
    private MarkAttendanceResult mResult;

    public MarkAttendanceResult getResult() {
        return mResult;
    }

    public void setResult(MarkAttendanceResult result) {
        mResult = result;
    }

}


package com.ominfo.hra_app.ui.attendance.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GetAttendanceResponse {

    @SerializedName("result")
    private GetAttendanceResult mResult;

    public GetAttendanceResult getResult() {
        return mResult;
    }

    public void setResult(GetAttendanceResult result) {
        mResult = result;
    }

}

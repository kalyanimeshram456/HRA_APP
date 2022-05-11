
package com.ominfo.hra_app.ui.attendance.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class UpdateAttendanceResponse {

    @SerializedName("result")
    private UpdateAttendanceResult mResult;

    public UpdateAttendanceResult getResult() {
        return mResult;
    }

    public void setResult(UpdateAttendanceResult result) {
        mResult = result;
    }

}

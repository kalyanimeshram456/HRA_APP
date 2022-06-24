
package com.ominfo.hra_app.ui.employees.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AttendanceEmployeeListResponse {

    @SerializedName("result")
    private AttendanceEmployeeListResult mResult;

    public AttendanceEmployeeListResult getResult() {
        return mResult;
    }

    public void setResult(AttendanceEmployeeListResult result) {
        mResult = result;
    }

}

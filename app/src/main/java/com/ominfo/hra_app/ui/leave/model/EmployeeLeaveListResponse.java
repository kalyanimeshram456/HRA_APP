
package com.ominfo.hra_app.ui.leave.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class EmployeeLeaveListResponse {

    @SerializedName("result")
    private EmployeeLeaveResult mResult;

    public EmployeeLeaveResult getResult() {
        return mResult;
    }

    public void setResult(EmployeeLeaveResult result) {
        mResult = result;
    }

}

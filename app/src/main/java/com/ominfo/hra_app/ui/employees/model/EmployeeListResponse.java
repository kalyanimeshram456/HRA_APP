
package com.ominfo.hra_app.ui.employees.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class EmployeeListResponse {

    @SerializedName("result")
    private EmployeeListResult mResult;

    public EmployeeListResult getResult() {
        return mResult;
    }

    public void setResult(EmployeeListResult result) {
        mResult = result;
    }

}

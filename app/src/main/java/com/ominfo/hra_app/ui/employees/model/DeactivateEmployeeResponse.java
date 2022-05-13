
package com.ominfo.hra_app.ui.employees.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class DeactivateEmployeeResponse {

    @SerializedName("result")
    private DeactivateEmployeeResult mResult;

    public DeactivateEmployeeResult getResult() {
        return mResult;
    }

    public void setResult(DeactivateEmployeeResult result) {
        mResult = result;
    }

}

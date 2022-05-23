
package com.ominfo.hra_app.ui.salary.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SalaryAllResponse {

    @SerializedName("result")
    private SalaryAllResult mResult;

    public SalaryAllResult getResult() {
        return mResult;
    }

    public void setResult(SalaryAllResult result) {
        mResult = result;
    }

}

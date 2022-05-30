
package com.ominfo.hra_app.ui.salary.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SalarySlipResponse {

    @SerializedName("result")
    private SalarySlipResult mResult;

    public SalarySlipResult getResult() {
        return mResult;
    }

    public void setResult(SalarySlipResult result) {
        mResult = result;
    }

}


package com.ominfo.hra_app.ui.salary.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SalarySheetResponse {

    @SerializedName("result")
    private SalarySheetResult mResult;

    public SalarySheetResult getResult() {
        return mResult;
    }

    public void setResult(SalarySheetResult result) {
        mResult = result;
    }

}


package com.ominfo.hra_app.ui.salary.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class UpdateSalaryResponse {

    @SerializedName("result")
    private UpdateSalaryResult mResult;

    public UpdateSalaryResult getResult() {
        return mResult;
    }

    public void setResult(UpdateSalaryResult result) {
        mResult = result;
    }

}

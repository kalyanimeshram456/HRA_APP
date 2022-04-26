
package com.ominfo.crm_solution.ui.my_account.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ApplyLeaveResponse {

    @SerializedName("result")
    private ApplyLeaveResult mResult;

    public ApplyLeaveResult getResult() {
        return mResult;
    }

    public void setResult(ApplyLeaveResult result) {
        mResult = result;
    }

}

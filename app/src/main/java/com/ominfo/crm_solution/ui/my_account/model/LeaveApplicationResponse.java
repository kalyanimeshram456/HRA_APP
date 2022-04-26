
package com.ominfo.crm_solution.ui.my_account.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LeaveApplicationResponse {

    @SerializedName("result")
    private LeaveApplicationResult mResult;

    public LeaveApplicationResult getResult() {
        return mResult;
    }

    public void setResult(LeaveApplicationResult result) {
        mResult = result;
    }

}

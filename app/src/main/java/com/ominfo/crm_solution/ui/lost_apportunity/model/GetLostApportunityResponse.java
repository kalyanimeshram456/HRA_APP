
package com.ominfo.crm_solution.ui.lost_apportunity.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GetLostApportunityResponse {

    @SerializedName("result")
    private LostApportunityResult mResult;

    public LostApportunityResult getResult() {
        return mResult;
    }

    public void setResult(LostApportunityResult result) {
        mResult = result;
    }

}

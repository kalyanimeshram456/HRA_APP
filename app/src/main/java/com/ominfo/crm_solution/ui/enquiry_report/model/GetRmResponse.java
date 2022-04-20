
package com.ominfo.crm_solution.ui.enquiry_report.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GetRmResponse {

    @SerializedName("result")
    private GetRmResult mResult;

    public GetRmResult getResult() {
        return mResult;
    }

    public void setResult(GetRmResult result) {
        mResult = result;
    }

}

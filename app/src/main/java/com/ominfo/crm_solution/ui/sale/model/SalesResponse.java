
package com.ominfo.crm_solution.ui.sale.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SalesResponse {

    @SerializedName("result")
    private SalesResult mResult;

    public SalesResult getResult() {
        return mResult;
    }

    public void setResult(SalesResult result) {
        mResult = result;
    }

}

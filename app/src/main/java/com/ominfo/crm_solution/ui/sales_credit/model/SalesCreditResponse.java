
package com.ominfo.crm_solution.ui.sales_credit.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SalesCreditResponse {

    @SerializedName("result")
    private SalesCreditResult mResult;

    public SalesCreditResult getResult() {
        return mResult;
    }

    public void setResult(SalesCreditResult result) {
        mResult = result;
    }

}

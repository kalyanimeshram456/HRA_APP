
package com.ominfo.crm_solution.ui.quotation_amount.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class QuotationResponse {

    @SerializedName("result")
    private QuotationResult mResult;

    public QuotationResult getResult() {
        return mResult;
    }

    public void setResult(QuotationResult result) {
        mResult = result;
    }

}


package com.ominfo.crm_solution.ui.receipt.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ReceiptResponse {

    @SerializedName("result")
    private ReceiptResultData mResult;

    public ReceiptResultData getResult() {
        return mResult;
    }

    public void setResult(ReceiptResultData result) {
        mResult = result;
    }

}

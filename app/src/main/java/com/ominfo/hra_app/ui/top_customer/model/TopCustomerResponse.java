
package com.ominfo.hra_app.ui.top_customer.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class TopCustomerResponse {

    @SerializedName("result")
    private TopCustResult mResult;

    public TopCustResult getResult() {
        return mResult;
    }

    public void setResult(TopCustResult result) {
        mResult = result;
    }

}

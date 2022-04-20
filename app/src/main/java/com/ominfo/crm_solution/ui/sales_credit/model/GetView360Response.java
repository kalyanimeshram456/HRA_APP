
package com.ominfo.crm_solution.ui.sales_credit.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GetView360Response {

    @SerializedName("result")
    private CustomerResult mResult;

    public CustomerResult getResult() {
        return mResult;
    }

    public void setResult(CustomerResult result) {
        mResult = result;
    }

}

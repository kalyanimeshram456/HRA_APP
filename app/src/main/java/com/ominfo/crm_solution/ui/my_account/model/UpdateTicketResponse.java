
package com.ominfo.crm_solution.ui.my_account.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class UpdateTicketResponse {

    @SerializedName("result")
    private UpdateTicketResult mResult;

    public UpdateTicketResult getResult() {
        return mResult;
    }

    public void setResult(UpdateTicketResult result) {
        mResult = result;
    }

}

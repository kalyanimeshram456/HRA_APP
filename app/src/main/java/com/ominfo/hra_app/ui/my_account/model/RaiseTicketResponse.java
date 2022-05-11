
package com.ominfo.hra_app.ui.my_account.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class RaiseTicketResponse {

    @SerializedName("result")
    private RaiseTicketResult mResult;

    public RaiseTicketResult getResult() {
        return mResult;
    }

    public void setResult(RaiseTicketResult result) {
        mResult = result;
    }

}

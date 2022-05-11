
package com.ominfo.hra_app.ui.my_account.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GetTicketResponse {

    @SerializedName("result")
    private GetTicketResult mResult;

    public GetTicketResult getResult() {
        return mResult;
    }

    public void setResult(GetTicketResult result) {
        mResult = result;
    }

}


package com.ominfo.crm_solution.ui.notifications.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LeaveSingleRecordResponse {

    @SerializedName("result")
    private LeaveSingleResult mResult;

    public LeaveSingleResult getResult() {
        return mResult;
    }

    public void setResult(LeaveSingleResult result) {
        mResult = result;
    }

}


package com.ominfo.hra_app.ui.my_account.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ChangeProfileImageResponse {

    @SerializedName("result")
    private ChangeProfileResult mResult;

    public ChangeProfileResult getResult() {
        return mResult;
    }

    public void setResult(ChangeProfileResult result) {
        mResult = result;
    }

}

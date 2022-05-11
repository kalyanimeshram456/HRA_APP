
package com.ominfo.hra_app.ui.my_account.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ChangePasswordResponse {

    @SerializedName("result")
    private ChangePasswordResult mResult;

    public ChangePasswordResult getResult() {
        return mResult;
    }

    public void setResult(ChangePasswordResult result) {
        mResult = result;
    }

}

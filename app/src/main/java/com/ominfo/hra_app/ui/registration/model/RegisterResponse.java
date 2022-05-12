
package com.ominfo.hra_app.ui.registration.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class RegisterResponse {

    @SerializedName("result")
    private RegisterResult mResult;

    public RegisterResult getResult() {
        return mResult;
    }

    public void setResult(RegisterResult result) {
        mResult = result;
    }

}


package com.ominfo.crm_solution.ui.login.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LoginResponse {

    @SerializedName("details")
    private LoginTable mDetails;
    @SerializedName("result")
    private Result mResult;

    public LoginTable getDetails() {
        return mDetails;
    }

    public void setDetails(LoginTable details) {
        mDetails = details;
    }

    public Result getResult() {
        return mResult;
    }

    public void setResult(Result result) {
        mResult = result;
    }

}


package com.ominfo.hra_app.ui.my_account.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ProfileImageResponse {

    @SerializedName("result")
    private ProfileImageResult mResult;

    public ProfileImageResult getResult() {
        return mResult;
    }

    public void setResult(ProfileImageResult result) {
        mResult = result;
    }

}

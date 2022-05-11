
package com.ominfo.hra_app.ui.my_account.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ProfileResponse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("result")
    private List<ProfileResult> mResult;
    @SerializedName("status")
    private Long mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<ProfileResult> getResult() {
        return mResult;
    }

    public void setResult(List<ProfileResult> result) {
        mResult = result;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

}

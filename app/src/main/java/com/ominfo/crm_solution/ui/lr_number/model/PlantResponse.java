
package com.ominfo.crm_solution.ui.lr_number.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class PlantResponse {

    @SerializedName("Message")
    private String mMessage;
    @SerializedName("Result")
    private List<PlantResponseResult> mResult;
    @SerializedName("Status")
    private String mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<PlantResponseResult> getResult() {
        return mResult;
    }

    public void setResult(List<PlantResponseResult> result) {
        mResult = result;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}


package com.ominfo.crm_solution.ui.lr_number.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class UploadVehicleRecordRespoonse {

    @SerializedName("Message")
    private String mMessage;
    @SerializedName("Result")
    private UploadVehicleRecordResResult mResult;
    @SerializedName("Status")
    private String mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public UploadVehicleRecordResResult getResult() {
        return mResult;
    }

    public void setResult(UploadVehicleRecordResResult result) {
        mResult = result;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
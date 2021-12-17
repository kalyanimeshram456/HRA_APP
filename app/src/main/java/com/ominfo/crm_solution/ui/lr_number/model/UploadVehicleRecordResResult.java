
package com.ominfo.crm_solution.ui.lr_number.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class UploadVehicleRecordResResult {

    @SerializedName("Error_Code")
    private String mErrorCode;
    @SerializedName("Error_Desc")
    private String mErrorDesc;
    @SerializedName("generated_id")
    private String mGeneratedId;

    public String getErrorCode() {
        return mErrorCode;
    }

    public void setErrorCode(String errorCode) {
        mErrorCode = errorCode;
    }

    public String getErrorDesc() {
        return mErrorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        mErrorDesc = errorDesc;
    }

    public String getGeneratedId() {
        return mGeneratedId;
    }

    public void setGeneratedId(String generatedId) {
        mGeneratedId = generatedId;
    }

}

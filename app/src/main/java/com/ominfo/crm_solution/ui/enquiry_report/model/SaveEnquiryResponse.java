
package com.ominfo.crm_solution.ui.enquiry_report.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SaveEnquiryResponse {

    @SerializedName("result")
    private SaveEnquiryResult mResult;

    public SaveEnquiryResult getResult() {
        return mResult;
    }

    public void setResult(SaveEnquiryResult result) {
        mResult = result;
    }

}

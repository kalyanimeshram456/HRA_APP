
package com.ominfo.crm_solution.ui.enquiry_report.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GetEnquiryResponse {

    @SerializedName("result")
    private GetEnquiryResult mResult;

    public GetEnquiryResult getResult() {
        return mResult;
    }

    public void setResult(GetEnquiryResult result) {
        mResult = result;
    }

}

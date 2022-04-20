
package com.ominfo.crm_solution.ui.enquiry_report.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SearchCustResponse {

    @SerializedName("result")
    private SearchCustResult mResult;

    public SearchCustResult getResult() {
        return mResult;
    }

    public void setResult(SearchCustResult result) {
        mResult = result;
    }

}

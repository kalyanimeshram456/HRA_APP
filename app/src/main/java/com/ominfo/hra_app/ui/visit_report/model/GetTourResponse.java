
package com.ominfo.hra_app.ui.visit_report.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GetTourResponse {

    @SerializedName("result")
    private GetTourResult mResult;

    public GetTourResult getResult() {
        return mResult;
    }

    public void setResult(GetTourResult result) {
        mResult = result;
    }

}

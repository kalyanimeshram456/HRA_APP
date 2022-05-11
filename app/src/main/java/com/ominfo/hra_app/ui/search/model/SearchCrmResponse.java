
package com.ominfo.hra_app.ui.search.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SearchCrmResponse {

    @SerializedName("result")
    private SearchCrmResult mResult;

    public SearchCrmResult getResult() {
        return mResult;
    }

    public void setResult(SearchCrmResult result) {
        mResult = result;
    }

}

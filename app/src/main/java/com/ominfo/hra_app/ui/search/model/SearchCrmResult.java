
package com.ominfo.hra_app.ui.search.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SearchCrmResult {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("searchresult")
    private List<Searchresult> mSearchresult;
    @SerializedName("status")
    private String mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<Searchresult> getSearchresult() {
        return mSearchresult;
    }

    public void setSearchresult(List<Searchresult> searchresult) {
        mSearchresult = searchresult;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}


package com.ominfo.crm_solution.ui.lr_number.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class VehicalNumberRequest {

    @SerializedName("SearchFor")
    private String mSearchFor;
    @SerializedName("userkey")
    private String mUserkey;

    public String getSearchFor() {
        return mSearchFor;
    }

    public void setSearchFor(String searchFor) {
        mSearchFor = searchFor;
    }

    public String getUserkey() {
        return mUserkey;
    }

    public void setUserkey(String userkey) {
        mUserkey = userkey;
    }

}

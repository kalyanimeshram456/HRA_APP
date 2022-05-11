
package com.ominfo.hra_app.ui.sales_credit.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class YearlyInvRecord {

    @SerializedName("InvAmount")
    private String mInvAmount;
    @SerializedName("InvCount")
    private String mInvCount;
    @SerializedName("Year")
    private String mYear;

    public String getInvAmount() {
        return mInvAmount;
    }

    public void setInvAmount(String invAmount) {
        mInvAmount = invAmount;
    }

    public String getInvCount() {
        return mInvCount;
    }

    public void setInvCount(String invCount) {
        mInvCount = invCount;
    }

    public String getYear() {
        return mYear;
    }

    public void setYear(String year) {
        mYear = year;
    }

}

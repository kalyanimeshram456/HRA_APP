
package com.ominfo.hra_app.ui.registration.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SubscriptionLeave {

    @SerializedName("created_date")
    private String mCreatedDate;
    @SerializedName("end_date")
    private String mEndDate;
    @SerializedName("from_date")
    private String mFromDate;
    @SerializedName("id")
    private String mId;
    @SerializedName("price_per_person")
    private String mPricePerPerson;

    public String getCreatedDate() {
        return mCreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        mCreatedDate = createdDate;
    }

    public String getEndDate() {
        return mEndDate;
    }

    public void setEndDate(String endDate) {
        mEndDate = endDate;
    }

    public String getFromDate() {
        return mFromDate;
    }

    public void setFromDate(String fromDate) {
        mFromDate = fromDate;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getPricePerPerson() {
        return mPricePerPerson;
    }

    public void setPricePerPerson(String pricePerPerson) {
        mPricePerPerson = pricePerPerson;
    }

}

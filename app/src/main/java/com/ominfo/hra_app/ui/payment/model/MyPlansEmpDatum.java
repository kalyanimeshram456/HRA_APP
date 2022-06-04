
package com.ominfo.hra_app.ui.payment.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class MyPlansEmpDatum {

    @SerializedName("company_id")
    private String mCompanyId;
    @SerializedName("price_per_person")
    private String mPricePerPerson;
    @SerializedName("staff_strength")
    private String mStaffStrength;
    @SerializedName("valid_from")
    private String mValidFrom;
    @SerializedName("valid_to")
    private String mValidTo;

    public String getCompanyId() {
        return mCompanyId;
    }

    public void setCompanyId(String companyId) {
        mCompanyId = companyId;
    }

    public String getPricePerPerson() {
        return mPricePerPerson;
    }

    public void setPricePerPerson(String pricePerPerson) {
        mPricePerPerson = pricePerPerson;
    }

    public String getStaffStrength() {
        return mStaffStrength;
    }

    public void setStaffStrength(String staffStrength) {
        mStaffStrength = staffStrength;
    }

    public String getValidFrom() {
        return mValidFrom;
    }

    public void setValidFrom(String validFrom) {
        mValidFrom = validFrom;
    }

    public String getValidTo() {
        return mValidTo;
    }

    public void setValidTo(String validTo) {
        mValidTo = validTo;
    }

}

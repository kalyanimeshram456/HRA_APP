
package com.ominfo.hra_app.ui.payment.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class RenewPlanEmpDatum {

    @SerializedName("company_active_count")
    private Object mCompanyActiveCount;
    @SerializedName("company_id")
    private String mCompanyId;
    @SerializedName("gst_amount")
    private String mGstAmount;
    @SerializedName("gst_percent")
    private String mGstPercent;
    @SerializedName("id")
    private String mId;
    @SerializedName("price_per_person")
    private String mPricePerPerson;
    @SerializedName("staff_strength")
    private String mStaffStrength;
    @SerializedName("sub_charge")
    private String mSubCharge;
    @SerializedName("total_charge")
    private String mTotalCharge;
    @SerializedName("valid_from")
    private String mValidFrom;
    @SerializedName("valid_to")
    private String mValidTo;

    public Object getCompanyActiveCount() {
        return mCompanyActiveCount;
    }

    public void setCompanyActiveCount(Object companyActiveCount) {
        mCompanyActiveCount = companyActiveCount;
    }

    public String getCompanyId() {
        return mCompanyId;
    }

    public void setCompanyId(String companyId) {
        mCompanyId = companyId;
    }

    public String getGstAmount() {
        return mGstAmount;
    }

    public void setGstAmount(String gstAmount) {
        mGstAmount = gstAmount;
    }

    public String getGstPercent() {
        return mGstPercent;
    }

    public void setGstPercent(String gstPercent) {
        mGstPercent = gstPercent;
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

    public String getStaffStrength() {
        return mStaffStrength;
    }

    public void setStaffStrength(String staffStrength) {
        mStaffStrength = staffStrength;
    }

    public String getSubCharge() {
        return mSubCharge;
    }

    public void setSubCharge(String subCharge) {
        mSubCharge = subCharge;
    }

    public String getTotalCharge() {
        return mTotalCharge;
    }

    public void setTotalCharge(String totalCharge) {
        mTotalCharge = totalCharge;
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


package com.ominfo.hra_app.ui.registration.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ApplyCouponList {

    @SerializedName("code")
    private String mCode;
    @SerializedName("company_ID")
    private String mCompanyID;
    @SerializedName("created_by")
    private String mCreatedBy;
    @SerializedName("created_on")
    private Object mCreatedOn;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("discount_amt")
    private String mDiscountAmt;
    @SerializedName("expiry_on")
    private String mExpiryOn;
    @SerializedName("id")
    private String mId;

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public String getCompanyID() {
        return mCompanyID;
    }

    public void setCompanyID(String companyID) {
        mCompanyID = companyID;
    }

    public String getCreatedBy() {
        return mCreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        mCreatedBy = createdBy;
    }

    public Object getCreatedOn() {
        return mCreatedOn;
    }

    public void setCreatedOn(Object createdOn) {
        mCreatedOn = createdOn;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDiscountAmt() {
        return mDiscountAmt;
    }

    public void setDiscountAmt(String discountAmt) {
        mDiscountAmt = discountAmt;
    }

    public String getExpiryOn() {
        return mExpiryOn;
    }

    public void setExpiryOn(String expiryOn) {
        mExpiryOn = expiryOn;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

}

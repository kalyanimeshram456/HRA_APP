
package com.ominfo.crm_solution.ui.product.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ProductResult {

    @SerializedName("company_ID")
    private Long mCompanyID;
    @SerializedName("created_by")
    private Long mCreatedBy;
    @SerializedName("created_on")
    private String mCreatedOn;
    @SerializedName("hsn_id")
    private Long mHsnId;
    @SerializedName("isActive")
    private String mIsActive;
    @SerializedName("isSynced")
    private Long mIsSynced;
    @SerializedName("item_typeofsupply")
    private Object mItemTypeofsupply;
    @SerializedName("min_price")
    private Long mMinPrice;
    @SerializedName("prod_code")
    private String mProdCode;
    @SerializedName("prod_description")
    private String mProdDescription;
    @SerializedName("prod_id")
    private Long mProdId;
    @SerializedName("prod_name")
    private String mProdName;
    @SerializedName("unit_id")
    private Long mUnitId;
    @SerializedName("updated_by")
    private Long mUpdatedBy;
    @SerializedName("updated_on")
    private String mUpdatedOn;

    public Long getCompanyID() {
        return mCompanyID;
    }

    public void setCompanyID(Long companyID) {
        mCompanyID = companyID;
    }

    public Long getCreatedBy() {
        return mCreatedBy;
    }

    public void setCreatedBy(Long createdBy) {
        mCreatedBy = createdBy;
    }

    public String getCreatedOn() {
        return mCreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        mCreatedOn = createdOn;
    }

    public Long getHsnId() {
        return mHsnId;
    }

    public void setHsnId(Long hsnId) {
        mHsnId = hsnId;
    }

    public String getIsActive() {
        return mIsActive;
    }

    public void setIsActive(String isActive) {
        mIsActive = isActive;
    }

    public Long getIsSynced() {
        return mIsSynced;
    }

    public void setIsSynced(Long isSynced) {
        mIsSynced = isSynced;
    }

    public Object getItemTypeofsupply() {
        return mItemTypeofsupply;
    }

    public void setItemTypeofsupply(Object itemTypeofsupply) {
        mItemTypeofsupply = itemTypeofsupply;
    }

    public Long getMinPrice() {
        return mMinPrice;
    }

    public void setMinPrice(Long minPrice) {
        mMinPrice = minPrice;
    }

    public String getProdCode() {
        return mProdCode;
    }

    public void setProdCode(String prodCode) {
        mProdCode = prodCode;
    }

    public String getProdDescription() {
        return mProdDescription;
    }

    public void setProdDescription(String prodDescription) {
        mProdDescription = prodDescription;
    }

    public Long getProdId() {
        return mProdId;
    }

    public void setProdId(Long prodId) {
        mProdId = prodId;
    }

    public String getProdName() {
        return mProdName;
    }

    public void setProdName(String prodName) {
        mProdName = prodName;
    }

    public Long getUnitId() {
        return mUnitId;
    }

    public void setUnitId(Long unitId) {
        mUnitId = unitId;
    }

    public Long getUpdatedBy() {
        return mUpdatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        mUpdatedBy = updatedBy;
    }

    public String getUpdatedOn() {
        return mUpdatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        mUpdatedOn = updatedOn;
    }

}

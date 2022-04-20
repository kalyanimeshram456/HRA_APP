
package com.ominfo.crm_solution.ui.receipt.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ReceiptResult {

    @SerializedName("amount")
    private String mAmount;
    @SerializedName("company_ID")
    private Long mCompanyID;
    @SerializedName("companyName")
    private String mCompanyName;
    @SerializedName("cust_id")
    private Long mCustId;
    @SerializedName("receiptNumber")
    private String mReceiptNumber;

    public String getAmount() {
        return mAmount;
    }

    public void setAmount(String amount) {
        mAmount = amount;
    }

    public Long getCompanyID() {
        return mCompanyID;
    }

    public void setCompanyID(Long companyID) {
        mCompanyID = companyID;
    }

    public String getCompanyName() {
        return mCompanyName;
    }

    public void setCompanyName(String companyName) {
        mCompanyName = companyName;
    }

    public Long getCustId() {
        return mCustId;
    }

    public void setCustId(Long custId) {
        mCustId = custId;
    }

    public String getReceiptNumber() {
        return mReceiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        mReceiptNumber = receiptNumber;
    }

}

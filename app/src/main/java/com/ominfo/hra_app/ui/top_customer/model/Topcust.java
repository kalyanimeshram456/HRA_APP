
package com.ominfo.hra_app.ui.top_customer.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Topcust {

    @SerializedName("cust_id")
    private String mCustId;
    @SerializedName("cust_name")
    private String mCustName;
    @SerializedName("total")
    private String mTotal;
    @SerializedName("total_invoice")
    private String mTotalInvoice;

    public String getCustId() {
        return mCustId;
    }

    public void setCustId(String custId) {
        mCustId = custId;
    }

    public String getCustName() {
        return mCustName;
    }

    public void setCustName(String custName) {
        mCustName = custName;
    }

    public String getTotal() {
        return mTotal;
    }

    public void setTotal(String total) {
        mTotal = total;
    }

    public String getTotalInvoice() {
        return mTotalInvoice;
    }

    public void setTotalInvoice(String totalInvoice) {
        mTotalInvoice = totalInvoice;
    }

}
